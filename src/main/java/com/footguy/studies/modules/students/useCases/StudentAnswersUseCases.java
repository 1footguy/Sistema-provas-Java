package com.footguy.studies.modules.students.useCases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footguy.studies.modules.questions.entities.QuestionEntity;
import com.footguy.studies.modules.questions.repositories.QuestionRepository;
import com.footguy.studies.modules.students.dto.StudentAnswerDTO;
import com.footguy.studies.modules.students.dto.VerifyCertificationDTO;
import com.footguy.studies.modules.students.entities.AnswerCertificationEntity;
import com.footguy.studies.modules.students.entities.CertificationsStudentEntity;
import com.footguy.studies.modules.students.entities.StudentEntity;
import com.footguy.studies.modules.students.repositories.CertificationRepository;
import com.footguy.studies.modules.students.repositories.StudentRepository;

@Service
public class StudentAnswersUseCases {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private CertificationRepository certificationRepository;
    @Autowired
    private VerifyCertificationUseCases verifyCertificationUseCases;
    
    public CertificationsStudentEntity execute(StudentAnswerDTO dto) throws Exception{

        var hasCertification = verifyCertificationUseCases.execute(new VerifyCertificationDTO(dto.getEmail(), dto.getTech()));

        if (hasCertification) {
            throw new Exception("Você já tirou sua certificação");
            
        }
        // verificar se estudante existe
        var student = studentRepository.findByEmail(dto.getEmail());
        UUID studentId;
        if (student.isEmpty()) {
            var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
            studentCreated = studentRepository.save(studentCreated);
            studentId = studentCreated.getId();
        } else {
            studentId = student.get().getId();

        }

        List<AnswerCertificationEntity> answerCertifications = new ArrayList<>();
        // buscar as alternativas da pergunta -> corretas ou incorretas

        List<QuestionEntity> questionEntity = questionRepository.findByTech(dto.getTech());
        // salvar informações da certificação

        AtomicInteger correctCount = new AtomicInteger(0);

        dto.getQuestionAnswers().stream().forEach(questionAnswers -> {
           var question = questionEntity.stream().filter(quest -> quest.getId().equals(questionAnswers.getQuestionId())).findFirst().get();
            var findCorrect = question.getAlternatives().stream().filter(alternative -> alternative.isCorrect()).findFirst().get();

            if (findCorrect.getId().equals(questionAnswers.getAlternativeId())) {
                questionAnswers.setIsCorrect(true);
                correctCount.incrementAndGet();
            } else{
                questionAnswers.setIsCorrect(false);
            }
            var answerCertificationEntity = AnswerCertificationEntity.builder()
            .answerID(questionAnswers.getAlternativeId())
            .questionID(questionAnswers.getQuestionId())
            .isCorrect(questionAnswers.getIsCorrect()).build();

            answerCertifications.add(answerCertificationEntity);
        });

        CertificationsStudentEntity certificationsStudentEntity = CertificationsStudentEntity.builder()
        .tech(dto.getTech())
        .studentId(studentId)
        .grade(correctCount.get())
        .build();

        var certificationCreated = certificationRepository.save(certificationsStudentEntity);

        answerCertifications.stream().forEach(answerCertification -> {
            answerCertification.setCertificationID(certificationsStudentEntity.getId());
            answerCertification.setCertificationsStudentEntity(certificationsStudentEntity);
        });

        certificationsStudentEntity.setAnswerCertificationEntities(answerCertifications);
        certificationRepository.save(certificationsStudentEntity);
        return certificationCreated;
    }
}
