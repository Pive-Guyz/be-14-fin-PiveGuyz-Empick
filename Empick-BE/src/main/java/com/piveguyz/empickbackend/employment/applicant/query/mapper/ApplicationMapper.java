package com.piveguyz.empickbackend.employment.applicant.query.mapper;

import com.piveguyz.empickbackend.employment.applicant.query.dto.ApplicationQueryDTO;

import java.util.List;

public interface ApplicationMapper {
    
    List<ApplicationQueryDTO> findAllApplication();
    ApplicationQueryDTO findApplicationByApplicantId(Integer applicantId);
    ApplicationQueryDTO findApplicationById(int id);
}
