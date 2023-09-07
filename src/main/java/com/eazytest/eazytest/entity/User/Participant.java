package com.eazytest.eazytest.entity.User;

import jakarta.persistence.*;
import lombok.*;

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
}
