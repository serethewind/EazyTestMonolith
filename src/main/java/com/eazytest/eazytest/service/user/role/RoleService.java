package com.eazytest.eazytest.service.user.role;

import com.eazytest.eazytest.dto.general.ResponseDto;
import com.eazytest.eazytest.dto.general.UserResponseDto;
import com.eazytest.eazytest.entity.user.Examiner;
import com.eazytest.eazytest.entity.user.Participant;
import com.eazytest.eazytest.entity.user.RoleType;
import com.eazytest.eazytest.entity.user.UserEntity;
import com.eazytest.eazytest.repository.User.ExaminerRepository;
import com.eazytest.eazytest.repository.User.ParticipantRepository;
import com.eazytest.eazytest.repository.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoleService implements RoleServiceInteface {
    private UserRepository userRepository;
    private ExaminerRepository examinerRepository;
    private ParticipantRepository participantRepository;


    @Override
    public ResponseDto assignParticipantRole(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        userEntity.getRoleTypes().add(RoleType.PARTICIPANT);
        Participant participant = Participant.builder()
                .userEntity(userEntity)
                .build();

        participantRepository.save(participant);
        userEntity.setParticipant(participant);
        userRepository.save(userEntity);

    return ResponseDto.builder()
            .message("Participant role successfully assigned to user")
            .userResponseDto(
                    UserResponseDto.builder()
                            .userId(userId)
                            .userName(userEntity.getUsername())
                            .build()
            )
            .build();
    }

    @Override
    public ResponseDto assignExaminerRole(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userEntity.getRoleTypes().add(RoleType.EXAMINER);

        Examiner examiner = Examiner.builder()
                .userEntity(userEntity)
                .build();
        examinerRepository.save(examiner);
        userEntity.setExaminer(examiner);
        userRepository.save(userEntity);

        return ResponseDto.builder()
                .message("Examiner role successfully assigned to user")
                .userResponseDto(
                        UserResponseDto.builder()
                                .userId(userId)
                                .userName(userEntity.getUsername())
                                .build()
                )
                .build();
    }
}
