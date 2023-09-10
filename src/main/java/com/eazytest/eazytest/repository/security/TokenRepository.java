package com.eazytest.eazytest.repository.security;

import com.eazytest.eazytest.entity.security.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    @Query("select t from Token t where t.userType.id = ?1 and t.expired = false and t.revoked = false")
    List<Token> findAllValidTokensByUser(Long id);

//    @Query("select t from Token t where t.userEntity.id = ?1 and t.expired = false and t.revoked = false")
//    List<Token> findAllValidTokensByUser(Long id);


}
