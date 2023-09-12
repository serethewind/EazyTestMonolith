package com.eazytest.eazytest.repository.user;

import com.eazytest.eazytest.entity.userType.ParticipantType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<ParticipantType, String> {
}
