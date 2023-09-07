package com.eazytest.eazytest.repository.security;

import com.eazytest.eazytest.entity.security.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    Optional<TokenEntity> findByToken(String token);

    @Query("select t from TokenEntity t where t.userEntity.id = ?1 and t.expired = false and t.revoked = false")
    List<TokenEntity> findAllValidTokensByUser(Long id);
}
