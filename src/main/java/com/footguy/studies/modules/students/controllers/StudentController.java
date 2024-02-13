package com.footguy.studies.modules.students.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.footguy.studies.modules.students.dto.StudentAnswerDTO;
import com.footguy.studies.modules.students.dto.VerifyCertificationDTO;
import com.footguy.studies.modules.students.useCases.StudentAnswersUseCases;
import com.footguy.studies.modules.students.useCases.VerifyCertificationUseCases;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private VerifyCertificationUseCases verifyCertificationUseCases;

    @Autowired
    private StudentAnswersUseCases studentAnswersUseCases;
    
    @PostMapping("/verifyCertification")
    public String verifyCertification(@RequestBody VerifyCertificationDTO verifyCertificationDTO){

        var result = this.verifyCertificationUseCases.execute(verifyCertificationDTO);
        if (result) {
            return "Usuário já fez a prova.";
        }
        return "Usuario pode fazer a prova";
    }

    @PostMapping("/certification/answers")
    public ResponseEntity<Object> certificationAnswer(@RequestBody StudentAnswerDTO studentAnswerDTO) throws Exception{
    try {
        var result = studentAnswersUseCases.execute(studentAnswerDTO);
        return ResponseEntity.ok().body(result);
        
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    }
}
