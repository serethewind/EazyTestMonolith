package com.eazytest.eazytest.service.user.role;

import com.eazytest.eazytest.dto.email.EmailDetails;
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
import com.eazytest.eazytest.service.email.EmailServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoleService implements RoleServiceInteface {
    private UserRepository userRepository;
    private ExaminerRepository examinerRepository;
    private ParticipantRepository participantRepository;
    private EmailServiceInterface emailServiceInterface;


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

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(userType.getEmail())
                .subject("Participant role successfully assigned")
                .messageBody(String.format("Hi " + userType.getUsername() + ", Your id is '%s'. ", participantType.getParticipantId()))
                .build();

        emailServiceInterface.sendSimpleMessage(emailDetails);

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

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(userType.getEmail())
                .subject("Examiner role successfully assigned")
                .messageBody(String.format("Hi " + userType.getUsername() + ", Your id is '%s'. ", examinerType.getExaminerId()))
                .build();

        emailServiceInterface.sendSimpleMessage(emailDetails);

        return ResponseUserTypeDto.builder()
                .message("Examiner role successfully assigned to user")
                .userTypeId(examinerType.getExaminerId())
                .userName(userType.getUsername())
                .build();
    }



}
