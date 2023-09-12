package com.eazytest.eazytest.repository.user;



import com.eazytest.eazytest.entity.userType.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserType, Long> {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    UserType findByEmail(String email);

    Optional<UserType> findByUsername(String username);

//    @Query("select u from UserEntity u where u.username = ?1 or u.email = ?2")
    Optional<UserType> findByUsernameOrEmail(String username, String email);
}
