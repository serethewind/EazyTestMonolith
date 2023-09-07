package com.eazytest.eazytest.entity.User;

import jakarta.persistence.*;
import lombok.*;

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

}
