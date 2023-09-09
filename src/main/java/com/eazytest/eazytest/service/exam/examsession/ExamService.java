package com.eazytest.eazytest.service.exam.examsession;

import com.eazytest.eazytest.dto.Exam.*;
import com.eazytest.eazytest.dto.general.ReadResponseDto;
import com.eazytest.eazytest.dto.general.ResponseDto;
import com.eazytest.eazytest.dto.general.UserResponseDto;
import com.eazytest.eazytest.entity.exam.ExamSession;
import com.eazytest.eazytest.entity.userType.Examiner;
import com.eazytest.eazytest.exception.BadRequestException;
import com.eazytest.eazytest.exception.ResourceNotFoundException;
import com.eazytest.eazytest.repository.User.ExaminerRepository;
import com.eazytest.eazytest.repository.exam.ExamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExamService implements ExamServiceInterface {
    private ExamRepository examRepository;
    private ExaminerRepository examinerRepository;

    @Override
    public ReadResponseDto createExamSession(ExamRequestDto examRequestDto) {
        Examiner examiner = examinerRepository.findById(examRequestDto.getExaminerId()).orElseThrow(() -> new ResourceNotFoundException("Examiner with examinerId : " + examRequestDto.getExaminerId() + "not found"));

        ExamSession examSession = ExamSession.builder()
                .examiner(examiner)
                .sessionName(examRequestDto.getSessionName())
                .sessionDescription(examRequestDto.getSessionDescription())
                .category(CategoryType.valueOf(examRequestDto.getCategory()))
                .numberOfQuestions(examRequestDto.getNumberOfQuestions())
                .isExamActive(false) //by default
                .isTimed(TimeType.valueOf(examRequestDto.getIsTimed()))
                .lengthOfTimeInMinutes((examRequestDto.getIsTimed().equals(String.valueOf(TimeType.DISABLED))) ? null : examRequestDto.getLengthOfTime())
                .build();

        examiner.getExamSessionList().add(examSession);
        examinerRepository.save(examiner);
        examRepository.save(examSession);

        return ReadResponseDto.builder()
                .message(String.format("Exam session with id: %s created successfully", examSession.getSessionId()))
                .examResponseDtoList(Collections.singletonList(
                        ExamResponseDto.builder()
                                .sessionName(examSession.getSessionName())
                                .sessionDescription(examSession.getSessionDescription())
                                .examinerId(examSession.getExaminer().getExaminerId())
                                .build()
                ))
                .build();

    }

    @Override
    public ReadResponseDto updateExamSession(String sessionId, ExamUpdateRequestDto examUpdateRequestDto) {

        //fetch the exam session by id, then ensure that the examiner id of this session matches the examiner in this updated request form.

        ExamSession examSession = examRepository.findById(examUpdateRequestDto.getSessionId()).orElseThrow(() -> new ResourceNotFoundException(String.format("Exam session with id: '%s' not found", examUpdateRequestDto.getSessionId())));

        if (!examSession.getExaminer().getExaminerId().equals(examUpdateRequestDto.getExaminerId())) {
            throw new ResourceNotFoundException("Bad request observed, not authorized to update exam session");
        }

        examSession.setSessionName(examUpdateRequestDto.getSessionName());
        examSession.setSessionDescription(examUpdateRequestDto.getSessionDescription());
        examSession.setNumberOfQuestions(examUpdateRequestDto.getNumberOfQuestions());
        examSession.setIsTimed(TimeType.valueOf(examUpdateRequestDto.getIsTimed()));
        examSession.setLengthOfTimeInMinutes(examUpdateRequestDto.getIsTimed().equals(String.valueOf(TimeType.DISABLED)) ? null : examUpdateRequestDto.getLengthOfTime());

        examRepository.save(examSession);

        return ReadResponseDto.builder()
                .message(String.format("Exam session with id: '%s' successfully updated", examUpdateRequestDto.getSessionId()))
                .examResponseDtoList(Collections.singletonList(
                        ExamResponseDto.builder()
                                .sessionName(examSession.getSessionName())
                                .sessionDescription(examSession.getSessionDescription())
                                .examinerId(examSession.getExaminer().getExaminerId())
                                .build()
                ))
                .build();

    }

    @Override
    public ReadResponseDto fetchExamSessionById(String sessionId) {
        ExamSession examSession = examRepository.findById(sessionId).orElseThrow(() -> new ResourceNotFoundException(String.format("Exam session with id: '%s' not found", sessionId)));

        return ReadResponseDto.builder()
                .message(String.format("Exam session with id: '%s' successfully found", sessionId))
                .examResponseDtoList(Collections.singletonList(
                        ExamResponseDto.builder()
                                .sessionName(examSession.getSessionName())
                                .sessionDescription(examSession.getSessionDescription())
                                .examinerId(examSession.getExaminer().getExaminerId())
                                .build()
                ))
                .build();
    }

    @Override
    public ReadResponseDto fetchAllExamSession() {
        List<ExamSession> examSessionList = examRepository.findAll();
        if (examSessionList.isEmpty()) {
            throw new ResourceNotFoundException("Resource not found");
        }

        return ReadResponseDto.builder()
                .message("List of exam session successfully fetched")
                .examResponseDtoList(
                        examSessionList.stream().map(examSession ->
                                ExamResponseDto.builder()
                                        .sessionName(examSession.getSessionName())
                                        .sessionDescription(examSession.getSessionDescription())
                                        .examinerId(examSession.getExaminer().getExaminerId())
                                        .build()).collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public ReadResponseDto fetchExamSessionByExaminer(String examinerId, String sessionId) {
        List<ExamSession> examSessionList = examRepository.findByExaminerId(examinerId);
        if (examSessionList.isEmpty()) {
            throw new ResourceNotFoundException(String.format("No exam session associated with Examiner with id: '%s'", examinerId));
        }

        ExamSession foundExamSession = examSessionList.stream().filter(exam -> exam.getSessionId().equals(sessionId)).findFirst().orElseThrow(() -> new ResourceNotFoundException(String.format("Exam session with id: '%s' not found", sessionId)));

        return ReadResponseDto.builder()
                .message(String.format("Exam session associated with Examiner with id: '%s' successfully fetched", examinerId))
                .examResponseDtoList(
                        Collections.singletonList(
                                ExamResponseDto.builder()
                                        .sessionName(foundExamSession.getSessionName())
                                        .sessionDescription(foundExamSession.getSessionDescription())
                                        .examinerId(foundExamSession.getExaminer().getExaminerId())
                                        .build()
                        )
                )
                .build();
    }

    @Override
    public ReadResponseDto fetchAllExamSessionByExaminer(String examinerId) {
        List<ExamSession> examSessionList = examRepository.findAll().stream().filter(examSession -> examSession.getExaminer().getExaminerId().equals(examinerId)).toList();

        if (examSessionList.isEmpty()) {
            throw new ResourceNotFoundException(String.format("No exam session associated with examiner with id: '%s'", examinerId));
        }

        return ReadResponseDto.builder()
                .message(String.format("All exam sessions created by examiner with id: '%s' successfully fetched", examinerId))
                .examResponseDtoList(
                        examSessionList.stream().map(examSession ->
                                ExamResponseDto.builder()
                                        .sessionName(examSession.getSessionName())
                                        .sessionDescription(examSession.getSessionDescription())
                                        .examinerId(examSession.getExaminer().getExaminerId())
                                        .build()).collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public ReadResponseDto initiateExamSessionForParticipants(ActivateSessionDto activateSessionDto) {
        /**
         * first confirm that the session exist
         * then ensure that the examinerId of the existing session actually matches the id of the person initiating the request
         *
         *or
         *
         * first ensure examiner exist
         * then ensure that the sessionId is part of the sessions belonging to the examiner
         * then check status
         * since by default, it is false, let this method return true
         * if the method is already true, throw an exception that the exam session is already active
         */

        Examiner examiner = examinerRepository.findById(activateSessionDto.getExaminerId()).orElseThrow(() -> new ResourceNotFoundException(String.format("Examiner with id: '%s' not found", activateSessionDto.getExaminerId())));

        ExamSession examSession = examiner.getExamSessionList().stream().filter(examSessionEntity -> examSessionEntity.getSessionId().equals(activateSessionDto.getSessionId())).findFirst().orElseThrow(() -> new BadRequestException("This session can only be activated by Examiner that owns the session"));

        if (examSession.getIsExamActive()) {
            throw new BadRequestException("Exam session is already initiated");
        }
        examSession.setIsExamActive(true);
        examRepository.save(examSession);

        return ReadResponseDto.builder()
                .message(String.format("Exam session with id: '%s' is now active for participants to take", activateSessionDto.getSessionId()))
                .examResponseDtoList(Collections.singletonList(
                        ExamResponseDto.builder()
                                .sessionName(examSession.getSessionName())
                                .sessionDescription(examSession.getSessionDescription())
                                .examinerId(examSession.getExaminer().getExaminerId())
                                .build()
                ))
                .build();
    }

    @Override
    public ReadResponseDto endExamSessionForParticipants(ActivateSessionDto activateSessionDto) {

        ExamSession examSession = examRepository.findById(activateSessionDto.getSessionId()).orElseThrow(() -> new ResourceNotFoundException(String.format("Exam session with id: '%s' not found.", activateSessionDto.getSessionId())));

        if (!examSession.getExaminer().getExaminerId().equals(activateSessionDto.getExaminerId())) {
            throw new BadRequestException("This session can only be ended by owner");
        }

        if (!examSession.getIsExamActive()) {
            throw new BadRequestException("Exam session is currently inactive. Bad request!");
        }

        examSession.setIsExamActive(false);
        examRepository.save(examSession);

        return ReadResponseDto.builder()
                .message(String.format("Exam session with id: '%s' has been made inactive and is unavailable for participants to take", activateSessionDto.getSessionId()))
                .examResponseDtoList(Collections.singletonList(
                        ExamResponseDto.builder()
                                .sessionName(examSession.getSessionName())
                                .sessionDescription(examSession.getSessionDescription())
                                .examinerId(examSession.getExaminer().getExaminerId())
                                .build()
                ))
                .build();
    }

    @Override
    public ReadResponseDto createExamSessionInBatch(List<ExamRequestDto> examRequestDtoList) {
     /*
     Confirm that the list is not empty by fetching the examinerId of the first item and checking whether it is null.
     check that this first Id is valid and exist
     then check if the rest of the ids equals the first
     then create a batch
      */
        String examinerId = examRequestDtoList.stream().map(ExamRequestDto::getExaminerId).findFirst().orElseThrow(() -> new BadRequestException("Empty Batches created"));

        Examiner examiner = examinerRepository.findById(examinerId).orElseThrow(() -> new ResourceNotFoundException(String.format("Examiner with id: '%s' does not exist", examinerId)));

        boolean allMatch = examRequestDtoList.stream().allMatch(examInstance -> examInstance.getExaminerId().equals(examinerId));

        if (!allMatch) {
            throw new BadRequestException("Examiner id doesn't match for all the exam sessions in the batch");
        }

       examRequestDtoList.stream().map(examRequestDto -> examRepository.save(
                ExamSession.builder()
                        .examiner(examiner)
                        .sessionName(examRequestDto.getSessionName())
                        .sessionDescription(examRequestDto.getSessionDescription())
                        .category(CategoryType.valueOf(examRequestDto.getCategory()))
                        .numberOfQuestions(examRequestDto.getNumberOfQuestions())
                        .isExamActive(false) //by default
                        .isTimed(TimeType.valueOf(examRequestDto.getIsTimed()))
                        .lengthOfTimeInMinutes((examRequestDto.getIsTimed().equals(String.valueOf(TimeType.DISABLED))) ? null : examRequestDto.getLengthOfTime())
                        .build()
        )).collect(Collectors.toList());

        return ReadResponseDto.builder()
                .message("Exam session batch successfully created")
                .examResponseDtoList(examRequestDtoList.stream().map(examRequestDto -> ExamResponseDto.builder()
                        .sessionName(examRequestDto.getSessionName())
                        .sessionDescription(examRequestDto.getSessionDescription())
                        .examinerId(examinerId)
                        .build()).collect(Collectors.toList()))
                .build();
    }
}
