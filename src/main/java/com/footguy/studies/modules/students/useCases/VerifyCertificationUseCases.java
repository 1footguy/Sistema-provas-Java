package com.footguy.studies.modules.students.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footguy.studies.modules.students.dto.VerifyCertificationDTO;
import com.footguy.studies.modules.students.repositories.CertificationRepository;

@Service
public class VerifyCertificationUseCases {
    
    @Autowired
    private CertificationRepository certificationRepository;

    public Boolean execute( VerifyCertificationDTO dto){

        var result = this.certificationRepository.findByStudentEmailAndTech(dto.getEmail(), dto.getTech());

        if (!result.isEmpty()) {
            return true;
        }
        return false;
    }
   
}
