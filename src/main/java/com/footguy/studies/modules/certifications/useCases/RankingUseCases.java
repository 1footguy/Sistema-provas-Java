package com.footguy.studies.modules.certifications.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footguy.studies.modules.students.entities.CertificationsStudentEntity;
import com.footguy.studies.modules.students.repositories.CertificationRepository;

@Service
public class RankingUseCases {

    @Autowired
    private CertificationRepository certificationRepository;

    public List<CertificationsStudentEntity> execute(){
        var result = certificationRepository.findTop10ByOrderByGradeDesc();
        return result;
    }
    
}
