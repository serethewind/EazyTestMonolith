package com.eazytest.eazytest.service.exam.examsession;

import com.eazytest.eazytest.dto.exam.*;
import com.eazytest.eazytest.dto.general.ReadResponseDto;
import com.eazytest.eazytest.entity.exam.ExamInstance;
import com.eazytest.eazytest.entity.userType.ExaminerType;
import com.eazytest.eazytest.exception.BadRequestException;
import com.eazytest.eazytest.exception.ResourceNotFoundException;
import com.eazytest.eazytest.repository.user.ExaminerRepository;
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
        ExaminerType examinerType = examinerRepository.findById(examRequestDto.getExaminerId()).orElseThrow(() -> new ResourceNotFoundException("Examiner with examinerId : " + examRequestDto.getExaminerId() + "not found"));

        ExamInstance examInstance = ExamInstance.builder()
                .examinerClass(examinerType)
                .sessionName(examRequestDto.getSessionName())
                .sessionDescription(examRequestDto.getSessionDescription())
                .category(CategoryType.valueOf(examRequestDto.getCategory()))
                .numberOfQuestions(examRequestDto.getNumberOfQuestions())
                .isExamActive(false) //by default
                .isTimed(TimeType.valueOf(examRequestDto.getIsTimed()))
                .lengthOfTimeInMinutes((examRequestDto.getIsTimed().equals(String.valueOf(TimeType.DISABLED))) ? Long.MAX_VALUE : examRequestDto.getLengthOfTime())
                .build();

        examRepository.save(examInstance);

        examinerType.getExamInstances().add(examInstance);
        examinerRepository.save(examinerType);


        return ReadResponseDto.builder()
                .message(String.format("Exam session with id: %s created successfully", examInstance.getSessionId()))
                .examResponseDtoList(Collections.singletonList(
                        ExamResponseDto.builder()
                                .sessionId(examInstance.getSessionId())
                                .sessionName(examInstance.getSessionName())
                                .sessionDescription(examInstance.getSessionDescription())
                                .examinerId(examInstance.getExaminerClass().getExaminerId())
                                .build()
                ))
                .build();

    }

    @Override
    public ReadResponseDto updateExamSession(String sessionId, ExamUpdateRequestDto examUpdateRequestDto) {

        //fetch the exam session by id, then ensure that the examiner id of this session matches the examiner in this updated request form.

        ExamInstance examInstance = examRepository.findById(sessionId).orElseThrow(() -> new ResourceNotFoundException(String.format("Exam session with id: '%s' not found", sessionId)));

        if (!examInstance.getExaminerClass().getExaminerId().equals(examUpdateRequestDto.getExaminerId())) {
            throw new ResourceNotFoundException("Bad request observed, not authorized to update exam session");
        }

        examInstance.setSessionName(examUpdateRequestDto.getSessionName());
        examInstance.setSessionDescription(examUpdateRequestDto.getSessionDescription());
        examInstance.setNumberOfQuestions(examUpdateRequestDto.getNumberOfQuestions());
        examInstance.setIsTimed(TimeType.valueOf(examUpdateRequestDto.getIsTimed()));
        examInstance.setLengthOfTimeInMinutes(examUpdateRequestDto.getIsTimed().equals(String.valueOf(TimeType.DISABLED)) ? Long.MAX_VALUE : examUpdateRequestDto.getLengthOfTime());

        examRepository.save(examInstance);

        return ReadResponseDto.builder()
                .message(String.format("Exam session with id: '%s' successfully updated", sessionId))
                .examResponseDtoList(Collections.singletonList(
                        ExamResponseDto.builder()
                                .sessionId(examInstance.getSessionId())
                                .sessionName(examInstance.getSessionName())
                                .sessionDescription(examInstance.getSessionDescription())
                                .examinerId(examInstance.getExaminerClass().getExaminerId())
                                .build()
                ))
                .build();

    }

    @Override
    public ReadResponseDto fetchExamSessionById(String sessionId) {
        ExamInstance examInstance = examRepository.findById(sessionId).orElseThrow(() -> new ResourceNotFoundException(String.format("Exam session with id: '%s' not found", sessionId)));

        return ReadResponseDto.builder()
                .message(String.format("Exam session with id: '%s' successfully found", sessionId))
                .examResponseDtoList(Collections.singletonList(
                        ExamResponseDto.builder()
                                .sessionId(examInstance.getSessionId())
                                .sessionName(examInstance.getSessionName())
                                .sessionDescription(examInstance.getSessionDescription())
                                .examinerId(examInstance.getExaminerClass().getExaminerId())
                                .build()
                ))
                .build();
    }

    @Override
    public ReadResponseDto fetchAllExamSession() {
        List<ExamInstance> examInstanceList = examRepository.findAll();
        if (examInstanceList.isEmpty()) {
            throw new ResourceNotFoundException("Resource not found");
        }

        return ReadResponseDto.builder()
                .message("List of exam session successfully fetched")
                .examResponseDtoList(
                        examInstanceList.stream().map(examInstance ->
                                ExamResponseDto.builder()
                                        .sessionId(examInstance.getSessionId())
                                        .sessionName(examInstance.getSessionName())
                                        .sessionDescription(examInstance.getSessionDescription())
                                        .examinerId(examInstance.getExaminerClass().getExaminerId())
                                        .build()).collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public ReadResponseDto fetchExamSessionByExaminer(String examinerId, String sessionId) {
        List<ExamInstance> examInstanceList = examRepository.findByExaminerId(examinerId);
        if (examInstanceList.isEmpty()) {
            throw new ResourceNotFoundException(String.format("No exam session associated with Examiner with id: '%s'", examinerId));
        }

        ExamInstance foundExamInstance = examInstanceList.stream().filter(exam -> exam.getSessionId().equals(sessionId)).findFirst().orElseThrow(() -> new ResourceNotFoundException(String.format("Exam session with id: '%s' not found", sessionId)));

        return ReadResponseDto.builder()
                .message(String.format("Exam session associated with Examiner with id: '%s' successfully fetched", examinerId))
                .examResponseDtoList(
                        Collections.singletonList(
                                ExamResponseDto.builder()
                                        .sessionId(foundExamInstance.getSessionId())
                                        .sessionName(foundExamInstance.getSessionName())
                                        .sessionDescription(foundExamInstance.getSessionDescription())
                                        .examinerId(foundExamInstance.getExaminerClass().getExaminerId())
                                        .build()
                        )
                )
                .build();
    }

    @Override
    public ReadResponseDto fetchAllExamSessionByExaminer(String examinerId) {
        List<ExamInstance> examInstanceList = examRepository.findAll().stream().filter(examInstance -> examInstance.getExaminerClass().getExaminerId().equals(examinerId)).toList();

        if (examInstanceList.isEmpty()) {
            throw new ResourceNotFoundException(String.format("No exam session associated with examiner with id: '%s'", examinerId));
        }

        return ReadResponseDto.builder()
                .message(String.format("All exam sessions created by examiner with id: '%s' successfully fetched", examinerId))
                .examResponseDtoList(
                        examInstanceList.stream().map(examInstance ->
                                ExamResponseDto.builder()
                                        .sessionId(examInstance.getSessionId())
                                        .sessionName(examInstance.getSessionName())
                                        .sessionDescription(examInstance.getSessionDescription())
                                        .examinerId(examInstance.getExaminerClass().getExaminerId())
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

        ExaminerType examinerType = examinerRepository.findById(activateSessionDto.getExaminerId()).orElseThrow(() -> new ResourceNotFoundException(String.format("Examiner with id: '%s' not found", activateSessionDto.getExaminerId())));

        ExamInstance examInstance = examinerType.getExamInstances().stream().filter(examInstanceEntity -> examInstanceEntity.getSessionId().equals(activateSessionDto.getSessionId())).findFirst().orElseThrow(() -> new BadRequestException("This session can only be activated by Examiner that owns the session"));

        if (examInstance.getIsExamActive()) {
            throw new BadRequestException("Exam session is already initiated");
        }
        examInstance.setIsExamActive(true);
        examRepository.save(examInstance);

        return ReadResponseDto.builder()
                .message(String.format("Exam session with id: '%s' is now active for participants to take", activateSessionDto.getSessionId()))
                .examResponseDtoList(Collections.singletonList(
                        ExamResponseDto.builder()
                                .sessionId(examInstance.getSessionId())
                                .sessionName(examInstance.getSessionName())
                                .sessionDescription(examInstance.getSessionDescription())
                                .examinerId(examInstance.getExaminerClass().getExaminerId())
                                .build()
                ))
                .build();
    }

    @Override
    public ReadResponseDto endExamSessionForParticipants(ActivateSessionDto activateSessionDto) {

        ExamInstance examInstance = examRepository.findById(activateSessionDto.getSessionId()).orElseThrow(() -> new ResourceNotFoundException(String.format("Exam session with id: '%s' not found.", activateSessionDto.getSessionId())));

        if (!examInstance.getExaminerClass().getExaminerId().equals(activateSessionDto.getExaminerId())) {
            throw new BadRequestException("This session can only be ended by owner");
        }

        if (!examInstance.getIsExamActive()) {
            throw new BadRequestException("Exam session is currently inactive. Bad request!");
        }

        examInstance.setIsExamActive(false);
        examRepository.save(examInstance);

        return ReadResponseDto.builder()
                .message(String.format("Exam session with id: '%s' has been made inactive and is unavailable for participants to take", activateSessionDto.getSessionId()))
                .examResponseDtoList(Collections.singletonList(
                        ExamResponseDto.builder()
                                .sessionId(examInstance.getSessionId())
                                .sessionName(examInstance.getSessionName())
                                .sessionDescription(examInstance.getSessionDescription())
                                .examinerId(examInstance.getExaminerClass().getExaminerId())
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

        ExaminerType examinerType = examinerRepository.findById(examinerId).orElseThrow(() -> new ResourceNotFoundException(String.format("Examiner with id: '%s' does not exist", examinerId)));

        boolean allMatch = examRequestDtoList.stream().allMatch(examInstance -> examInstance.getExaminerId().equals(examinerId));

        if (!allMatch) {
            throw new BadRequestException("Examiner id doesn't match for all the exam sessions in the batch");
        }

     List<ExamInstance> examInstanceList =  examRequestDtoList.stream().map(examRequestDto -> examRepository.save(
                ExamInstance.builder()
                        .examinerClass(examinerType)
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
                .examResponseDtoList(examInstanceList.stream().map(examInstance -> ExamResponseDto.builder()
                        .sessionId(examInstance.getSessionId())
                        .sessionName(examInstance.getSessionName())
                        .sessionDescription(examInstance.getSessionDescription())
                        .examinerId(examinerId)
                        .build()).collect(Collectors.toList()))
                .build();
    }
}
