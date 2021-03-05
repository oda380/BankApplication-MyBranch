package com.finalproject.BankApplication.repository;

import com.finalproject.BankApplication.model.Assessment;
import com.finalproject.BankApplication.model.AssessmentStatus;
import com.finalproject.BankApplication.model.AssessmentType;
import com.finalproject.BankApplication.model.Decision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment,Integer> {
    public Assessment findFirstByOrderByIdDesc();
    public Assessment findAssessmentById(int id);
    public List<Assessment> findAssessmentByType(AssessmentType type);
    public List<Assessment> findAssessmentByStatus(AssessmentStatus status);

    @Modifying
    @Transactional
    @Query(value = "update Assessment a set a.status = :status where a.id = :id")
    void changeStatus(@Param("status") AssessmentStatus status, @Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "update Assessment a set a.type = :type where a.id = :id")
    void changeType(@Param("type") AssessmentType type, @Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "update Assessment a set a.decision = :decision where a.id = :id",nativeQuery = true)
    void changeDecision(@Param("decision") Decision decision, @Param("id") int id);

}

