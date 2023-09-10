package com.eazytest.eazytest.entity.userType;

import com.eazytest.eazytest.entity.exam.ExamInstance;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ExaminerType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true)
    private String examinerId;
    @OneToOne
    @JoinColumn(name = "user_entity_id")
    private UserType userType;
    @OneToMany(mappedBy = "examinerClass", cascade = CascadeType.ALL)
    private List<ExamInstance> examInstances = new ArrayList<>();

//    private List<ExamSession> examSessions;
}
