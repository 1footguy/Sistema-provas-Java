package com.footguy.studies.modules.certifications.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.footguy.studies.modules.certifications.useCases.RankingUseCases;
import com.footguy.studies.modules.students.entities.CertificationsStudentEntity;

@RestController
@RequestMapping("/ranking")
public class RankingController {
    @Autowired
    private RankingUseCases rankingUseCases;

    @GetMapping("/top10")
    public List<CertificationsStudentEntity> top10(){
        return rankingUseCases.execute();
    }
}
