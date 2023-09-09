package com.eazytest.eazytest.entity.user;

import com.eazytest.eazytest.entity.exam.ExamSession;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Examiner {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true)
    private String examinerId;
    @OneToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;
    @OneToMany(mappedBy = "examiner")
    private List<ExamSession> examSessionList;

}
