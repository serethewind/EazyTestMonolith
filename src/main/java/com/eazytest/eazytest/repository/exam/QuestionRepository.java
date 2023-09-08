package com.eazytest.eazytest.repository.exam;

import com.eazytest.eazytest.entity.exam.QuestionInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionInstance, Long> {
}
