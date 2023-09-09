package com.eazytest.eazytest.repository.exam;

import com.eazytest.eazytest.entity.exam.ExamSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<ExamSession, String> {

    List<ExamSession> findByExaminerId(String examinerId);
}
