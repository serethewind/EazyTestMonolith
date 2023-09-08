package com.eazytest.eazytest.service.exam.examsession;

import com.eazytest.eazytest.dto.Exam.ActivateSessionDto;
import com.eazytest.eazytest.dto.Exam.ExamRequestDto;
import com.eazytest.eazytest.dto.general.ResponseDto;
import com.eazytest.eazytest.entity.exam.ExamSession;
import com.eazytest.eazytest.entity.user.Examiner;
import com.eazytest.eazytest.exception.ResourceNotFoundException;
import com.eazytest.eazytest.repository.User.ExaminerRepository;
import com.eazytest.eazytest.repository.exam.ExamRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExamService implements ExamServiceInterface{
    private ExamRepository examRepository;
    private ExaminerRepository examinerRepository;

    @Override
    public ResponseDto createExamSession(ExamRequestDto examRequestDto) {
      Examiner examiner = examinerRepository.findById(examRequestDto.getExaminerId()).orElseThrow(() -> new ResourceNotFoundException("Examiner with examinerId : " + examRequestDto.getExaminerId() + "not found"));

        ExamSession.builder()
                .examiner(examiner)
                .sessionName(examRequestDto.getSessionName())
                .sessionDescription(examRequestDto.getSessionDescription())
                .
                .build();

    }

    @Override
    public ResponseDto updateExamSession(String sessionId, ExamRequestDto examRequestDto) {
        return null;
    }

    @Override
    public ResponseDto fetchExamSessionById(String sessionId) {
        return null;
    }

    @Override
    public List<ResponseDto> fetchAllExamSessionByExaminer(String examinerId) {
        return null;
    }

    @Override
    public ResponseDto initiateExamSessionForParticipants(ActivateSessionDto activateSessionDto) {
        return null;
    }

    @Override
    public ResponseDto endExamSessionForParticipants(ActivateSessionDto activateSessionDto) {
        return null;
    }
}
