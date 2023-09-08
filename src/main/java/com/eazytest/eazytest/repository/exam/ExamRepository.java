package com.eazytest.eazytest.repository.exam;

import com.eazytest.eazytest.entity.exam.ExamSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<ExamSession, String> {
}
