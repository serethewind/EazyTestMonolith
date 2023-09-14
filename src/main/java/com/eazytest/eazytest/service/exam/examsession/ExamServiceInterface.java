package com.eazytest.eazytest.service.exam.examsession;
import com.eazytest.eazytest.dto.exam.*;
import com.eazytest.eazytest.dto.general.ReadResponseDto;
import com.eazytest.eazytest.dto.question.PageableResponseDto;

import java.util.List;

public interface ExamServiceInterface {
    ReadResponseDto createExamSession(ExamRequestDto examRequestDto);

    ReadResponseDto updateExamSession(String sessionId, ExamUpdateRequestDto examUpdateRequestDto);

    ReadResponseDto fetchExamSessionById(String sessionId);

    ReadResponseDto fetchAllExamSession();

    ReadResponseDto fetchExamSessionByExaminer(String examinerId, String sessionId);

    ReadResponseDto fetchActiveExamSessions();

    ReadResponseDto fetchAllExamSessionByExaminer(String examinerId);

    ReadResponseDto initiateExamSessionForParticipants(ActivateSessionDto activateSessionDto);

    ReadResponseDto endExamSessionForParticipants(ActivateSessionDto activateSessionDto);

    ReadResponseDto createExamSessionInBatch(List<ExamRequestDto> examRequestDtoList);

    SessionWithGeneratedQuestionsDto generateQuestionsForExamSession(String sessionId, int pageNo, int PageSize);

    SessionWithGeneratedQuestionsDto viewExamSessionForParticipant(TakeExamSessionDto takeExamSessionDto, int pageNo, int pageSize);
}
