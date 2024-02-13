package com.footguy.studies.modules.students.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StudentAnswerDTO {

    private String email;
    private String tech;
    private List<QuestionAnswerDTO> questionAnswers;
    
}
