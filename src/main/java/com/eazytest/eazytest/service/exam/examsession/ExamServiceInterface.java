package com.eazytest.eazytest.service.exam.examsession;
import com.eazytest.eazytest.dto.Exam.ActivateSessionDto;
import com.eazytest.eazytest.dto.Exam.ExamRequestDto;
import com.eazytest.eazytest.dto.general.ResponseDto;

import java.util.List;

public interface ExamServiceInterface {
    ResponseDto createExamSession(ExamRequestDto examRequestDto);

    ResponseDto updateExamSession(String sessionId, ExamRequestDto examRequestDto);

    ResponseDto fetchExamSessionById(String sessionId);

    List<ResponseDto> fetchAllExamSessionByExaminer(String examinerId);

    ResponseDto initiateExamSessionForParticipants(ActivateSessionDto activateSessionDto);

    ResponseDto endExamSessionForParticipants(ActivateSessionDto activateSessionDto);
}
