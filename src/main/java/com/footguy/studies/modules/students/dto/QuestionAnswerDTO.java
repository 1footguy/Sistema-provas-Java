package com.footguy.studies.modules.students.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QuestionAnswerDTO {
    private UUID questionId;
    private UUID alternativeId;
    private Boolean isCorrect;
}
