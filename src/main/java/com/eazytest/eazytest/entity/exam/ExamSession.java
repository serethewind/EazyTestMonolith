package com.eazytest.eazytest.entity.exam;

import com.eazytest.eazytest.dto.Exam.CategoryType;
import com.eazytest.eazytest.entity.user.Examiner;
import com.eazytest.eazytest.entity.user.Participant;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "enrolled_course")
public class ExamSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String sessionId;
    @ManyToOne
    @JoinColumn(name = "examiner_id")
    private Examiner examiner;
    private String sessionName;
    private String sessionDescription;
    private CategoryType category;
    private Long numberOfQuestions;
    private Boolean isExamActive;
    private List<Long> questionsList;
    @ManyToMany
    @JoinTable(name = "participant_list",
            joinColumns = @JoinColumn(name = "examSessionId"),
            inverseJoinColumns = @JoinColumn(name = "participantId")
    )
    private List<Participant> participant;
    @CreationTimestamp
    private LocalDateTime sessionCreatedDate;
}