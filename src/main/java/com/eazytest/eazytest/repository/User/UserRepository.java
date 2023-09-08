package com.eazytest.eazytest.repository.User;

import com.eazytest.eazytest.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    UserEntity findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    @Query("select u from UserEntity u where u.username = ?1 or u.email = ?2")
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);
}
