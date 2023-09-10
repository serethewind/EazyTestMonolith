package com.eazytest.eazytest.repository.User;

import com.eazytest.eazytest.entity.userType.ExaminerType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExaminerRepository extends JpaRepository<ExaminerType, String> {
}
