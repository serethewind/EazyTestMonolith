package com.eazytest.eazytest.repository.exam;

import com.eazytest.eazytest.entity.exam.ExamInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExamRepository extends JpaRepository<ExamInstance, String> {
    @Query("select e from ExamInstance e where e.examinerClass.examinerId = ?1")
    List<ExamInstance> findByExaminerId(String examinerId);

//    @Query("select e from ExamSession e where e.examiner.examinerId = ?1")
//    List<ExamInstance> findByExaminerId(String examinerId);

}
