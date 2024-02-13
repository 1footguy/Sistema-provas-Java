package com.footguy.studies.modules.questions.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.footguy.studies.modules.questions.entities.QuestionEntity;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, UUID> {
    
    List<QuestionEntity> findByTech(String tech);
}
