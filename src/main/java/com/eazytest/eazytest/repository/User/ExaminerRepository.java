package com.eazytest.eazytest.repository.User;

import com.eazytest.eazytest.entity.User.Examiner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExaminerRepository extends JpaRepository<Examiner, String> {
}
