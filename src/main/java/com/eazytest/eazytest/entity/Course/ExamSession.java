package com.eazytest.eazytest.entity.Course;

import com.eazytest.eazytest.entity.User.Examiner;
import com.eazytest.eazytest.entity.User.Participant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "enrolled_course")
public class ExamSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String sessionId;
    private String sessionName;
    private String sessionDescription;
    @CreationTimestamp
    private LocalDateTime sessionCreatedDate;
    private List<Long> questionsList;
}