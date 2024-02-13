package com.footguy.studies.modules.students.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.footguy.studies.modules.students.entities.CertificationsStudentEntity;

@Repository
public interface CertificationRepository extends JpaRepository<CertificationsStudentEntity, UUID> {

    @Query("SELECT c FROM certifications c INNER join c.studentEntity std where std.email = :email AND c.tech = :tech")
    List<CertificationsStudentEntity> findByStudentEmailAndTech(String email, String tech);
    @Query("SELECT c FROM certifications c ORDER BY c.grade DESC LIMIT 10")
    List<CertificationsStudentEntity> findTop10ByOrderByGradeDesc();
}
