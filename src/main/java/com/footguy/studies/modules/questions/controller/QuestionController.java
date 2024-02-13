package com.footguy.studies.modules.questions.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.footguy.studies.modules.questions.dto.AlternativeResultDTO;
import com.footguy.studies.modules.questions.dto.QuestionResultDTO;
import com.footguy.studies.modules.questions.entities.AlternativesEntity;
import com.footguy.studies.modules.questions.entities.QuestionEntity;
import com.footguy.studies.modules.questions.repositories.QuestionRepository;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/tech/{tech}")
    public List<QuestionResultDTO> findByTech(@PathVariable String tech){
        var result = questionRepository.findByTech(tech);

        return result.stream().map(question -> mapQuestionToDTO(question))
                .collect(Collectors.toList());
    }

    static QuestionResultDTO mapQuestionToDTO(QuestionEntity question){
        var questionResultDTO = QuestionResultDTO.builder()
                .id(question.getId())
                .tech(question.getTech())
                .description(question.getDescription()).build();

        List<AlternativeResultDTO> alternativeResultDTOs = 
        question.getAlternatives()
        .stream().map(alternative -> mapAlternativeDTO(alternative))
        .collect(Collectors.toList());

        questionResultDTO.setAlternatives(alternativeResultDTOs);
        return questionResultDTO;
    }

    static AlternativeResultDTO mapAlternativeDTO(AlternativesEntity alternativesResultDTO){
        return AlternativeResultDTO.builder()
        .id(alternativesResultDTO.getId())
        .description(alternativesResultDTO.getDescription()).build();
    }
}
