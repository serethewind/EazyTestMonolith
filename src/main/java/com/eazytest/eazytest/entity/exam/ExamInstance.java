package com.eazytest.eazytest.entity.exam;

import com.eazytest.eazytest.dto.exam.CategoryType;
import com.eazytest.eazytest.dto.exam.TimeType;
import com.eazytest.eazytest.entity.userType.ExaminerType;
import com.eazytest.eazytest.entity.userType.ParticipantType;
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
public class ExamInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String sessionId;

    @ManyToOne
    @JoinColumn(name = "examiner_id")
    private ExaminerType examinerClass;
    private String sessionName;
    private String sessionDescription;

    private CategoryType category;
    private Integer numberOfQuestions;
    private Boolean isExamActive;

    private TimeType isTimed;
    private Long lengthOfTimeInMinutes;
    @ElementCollection
    private List<Long> questionsList;
    @ManyToMany
    @JoinTable(name = "participant_list",
            joinColumns = @JoinColumn(name = "examSessionId"),
            inverseJoinColumns = @JoinColumn(name = "participantId")
    )
    private List<ParticipantType> participantType;
    @CreationTimestamp
    private LocalDateTime sessionCreatedDate;
}