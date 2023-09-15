package com.eazytest.eazytest.service.user.role;

import com.eazytest.eazytest.dto.general.ResponseDto;
import com.eazytest.eazytest.dto.general.ResponseUserTypeDto;
import com.eazytest.eazytest.dto.general.UserResponseDto;
import com.eazytest.eazytest.entity.userType.ExaminerType;
import com.eazytest.eazytest.entity.userType.ParticipantType;
import com.eazytest.eazytest.entity.userType.RoleEnum;
import com.eazytest.eazytest.entity.userType.UserType;
import com.eazytest.eazytest.repository.user.ExaminerRepository;
import com.eazytest.eazytest.repository.user.ParticipantRepository;
import com.eazytest.eazytest.repository.user.UserRepository;
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
    public ResponseUserTypeDto assignParticipantRole(Long userId) {
        UserType userType = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        userType.getRoleEnums().add(RoleEnum.PARTICIPANT);
        ParticipantType participantType = ParticipantType.builder()
                .userType(userType)
                .build();

        participantRepository.save(participantType);
        userType.setParticipantType(participantType);
        userRepository.save(userType);

    return ResponseUserTypeDto.builder()
            .message("Participant role successfully assigned to user")
            .userTypeId(participantType.getParticipantId())
            .userName(userType.getUsername())
            .build();
    }

    @Override
    public ResponseUserTypeDto assignExaminerRole(Long userId) {
        UserType userType = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userType.getRoleEnums().add(RoleEnum.EXAMINER);

        ExaminerType examinerType = ExaminerType.builder()
                .userType(userType)
                .build();
        examinerRepository.save(examinerType);
        userType.setExaminerType(examinerType);
        userRepository.save(userType);

        return ResponseUserTypeDto.builder()
                .message("Examiner role successfully assigned to user")
                .userTypeId(examinerType.getExaminerId())
                .userName(userType.getUsername())
                .build();
    }
}
