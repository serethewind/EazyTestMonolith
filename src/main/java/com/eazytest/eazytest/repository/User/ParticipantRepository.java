package com.eazytest.eazytest.repository.User;

import com.eazytest.eazytest.entity.User.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, String> {
}
