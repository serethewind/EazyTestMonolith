package com.eazytest.eazytest.service.exam.question;

import com.eazytest.eazytest.dto.general.ReadQuestionResponseDto;
import com.eazytest.eazytest.dto.question.QuestionRequestDto;

import java.util.List;

public interface QuestionServiceInterface {
    ReadQuestionResponseDto createSingleQuestionInstance(QuestionRequestDto questionRequestDto);

    ReadQuestionResponseDto createMultipleQuestion(List<QuestionRequestDto> questionRequestDtoList);

    ReadQuestionResponseDto fetchAllQuestions(int pageNo, int pageSize);

    ReadQuestionResponseDto findQuestionById(Long questionId);

    ReadQuestionResponseDto findQuestionsByCategory(String category, int pageNo, int pageSize);

    ReadQuestionResponseDto findQuestionBySearchQuery(String searchWord, int pageNo, int pageSize);

    ReadQuestionResponseDto updateQuestionInstance(Long questionId, QuestionRequestDto questionRequestDto);

    ReadQuestionResponseDto deleteQuestionById(Long questionId);

    ReadQuestionResponseDto reactivateQuestionById(Long questionId);
}
