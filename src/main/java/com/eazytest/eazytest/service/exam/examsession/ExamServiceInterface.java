package com.eazytest.eazytest.service.exam.examsession;
import com.eazytest.eazytest.dto.Exam.ActivateSessionDto;
import com.eazytest.eazytest.dto.Exam.ExamRequestDto;
import com.eazytest.eazytest.dto.Exam.ExamUpdateRequestDto;
import com.eazytest.eazytest.dto.general.ReadResponseDto;
import com.eazytest.eazytest.dto.general.ResponseDto;

import java.util.List;

public interface ExamServiceInterface {
    ResponseDto createExamSession(ExamRequestDto examRequestDto);

    ResponseDto updateExamSession(String sessionId, ExamUpdateRequestDto examUpdateRequestDto);

    ReadResponseDto fetchExamSessionById(String sessionId);

    ReadResponseDto fetchAllExamSession();

    ReadResponseDto fetchExamSessionByExaminer(String examinerId, String sessionId);

    ReadResponseDto fetchAllExamSessionByExaminer(String examinerId);

    ReadResponseDto initiateExamSessionForParticipants(ActivateSessionDto activateSessionDto);

    ReadResponseDto endExamSessionForParticipants(ActivateSessionDto activateSessionDto);
}
