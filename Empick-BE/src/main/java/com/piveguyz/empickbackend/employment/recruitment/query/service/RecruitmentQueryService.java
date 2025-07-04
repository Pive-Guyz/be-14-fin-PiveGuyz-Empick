package com.piveguyz.empickbackend.employment.recruitment.query.service;

import java.util.List;

import com.piveguyz.empickbackend.employment.applicant.query.dto.ApplicantQueryDTO;
import com.piveguyz.empickbackend.employment.applicant.query.dto.ApplicationQueryDTO;
import com.piveguyz.empickbackend.employment.recruitment.query.dto.RecruitmentQueryConditionDTO;
import com.piveguyz.empickbackend.employment.recruitment.query.dto.RecruitmentQueryDTO;

public interface RecruitmentQueryService {
	List<RecruitmentQueryDTO> findRecruitments(RecruitmentQueryConditionDTO condition);

	RecruitmentQueryDTO findById(int recruitmentId);

	List<ApplicationQueryDTO> findApplicationsByRecruitmentId(int recruitmentId);
}
