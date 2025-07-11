package com.piveguyz.empickbackend.employment.jobtests.evaluation.command.application.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEvaluationResultCommandDTO {
    private String evaluatorComment;
    private Double score;

    private int applicationJobtestId;
    private int jobtestEvaluationCriteriaId;
}
