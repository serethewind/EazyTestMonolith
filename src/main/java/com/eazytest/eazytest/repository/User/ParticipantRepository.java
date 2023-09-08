package com.eazytest.eazytest.repository.User;

import com.eazytest.eazytest.entity.user.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, String> {
}
