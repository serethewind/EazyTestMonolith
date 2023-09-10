package com.eazytest.eazytest.entity.security;

import com.eazytest.eazytest.entity.userType.UserType;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private Boolean expired;
    private Boolean revoked;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserType userType;
}
