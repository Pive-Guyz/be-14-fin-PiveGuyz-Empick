package com.piveguyz.empickbackend.auth.command.application.service;

import com.piveguyz.empickbackend.auth.command.application.dto.LoginRequestDTO;
import com.piveguyz.empickbackend.auth.command.application.dto.LoginResponseDTO;
import com.piveguyz.empickbackend.auth.command.jwt.JwtProvider;
import com.piveguyz.empickbackend.common.exception.BusinessException;
import com.piveguyz.empickbackend.common.response.ResponseCode;
import com.piveguyz.empickbackend.orgstructure.member.command.domain.aggregate.MemberEntity;
import com.piveguyz.empickbackend.orgstructure.member.command.domain.repository.MemberRepository;
import com.piveguyz.empickbackend.orgstructure.member.query.dto.MemberRoleQueryDTO;
import com.piveguyz.empickbackend.orgstructure.member.query.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthCommandServiceImpl implements AuthCommandService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final MemberMapper memberMapper;
    private final RefreshTokenService refreshTokenService;

    @Override
    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        MemberEntity member = memberRepository.findByEmployeeNumber(
                Integer.parseInt(
                        requestDTO.getEmployeeNumber()
                )
        ).orElseThrow(() -> new BusinessException(ResponseCode.BAD_REQUEST));
        if (!passwordEncoder.matches(requestDTO.getPassword(), member.getPassword())) {
            log.error("Wrong password");
            log.info(String.valueOf(requestDTO));
            throw new BusinessException(ResponseCode.BAD_REQUEST); // 로그인 실패의 정확한 원인을 밝히지 않기 위함
        }

        if (member.getResignAt() != null) {
            throw new BusinessException(ResponseCode.MEMBER_RESIGNED);
        }

        if (member.getStatus() == 0) {
            throw new BusinessException(ResponseCode.MEMBER_STATUS_SUSPENDED);
        }

        List<MemberRoleQueryDTO> roles = memberMapper.findRolesByEmployeeNumber(member.getEmployeeNumber());
        List<String> roleCodes = roles.stream()
                .map(MemberRoleQueryDTO::getCode)
                .collect(Collectors.toList());

        String accessToken = jwtProvider.createAccessToken(member.getId(), member.getEmployeeNumber(), roleCodes);
        String refreshToken = jwtProvider.createRefreshToken(member.getId(), member.getEmployeeNumber());

        refreshTokenService.saveRefreshToken(
                member.getId(),
                refreshToken,
                1000 * 60 * 60 * 24 * 7
        );

        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
