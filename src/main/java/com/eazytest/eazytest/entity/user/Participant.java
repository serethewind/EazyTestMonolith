package com.eazytest.eazytest.entity.user;

import com.eazytest.eazytest.entity.exam.ExamSession;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true)
    private String participantId;
    @OneToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;
    @ManyToMany(mappedBy = "participant")
    private List<ExamSession> examSessionList;
}
