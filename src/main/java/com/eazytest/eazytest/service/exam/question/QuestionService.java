package com.eazytest.eazytest.service.exam.question;

import com.eazytest.eazytest.dto.exam.CategoryType;
import com.eazytest.eazytest.dto.general.ReadQuestionResponseDto;
import com.eazytest.eazytest.dto.question.Difficulty;
import com.eazytest.eazytest.dto.question.PageableResponseDto;
import com.eazytest.eazytest.dto.question.QuestionRequestDto;
import com.eazytest.eazytest.dto.question.QuestionResponseDto;
import com.eazytest.eazytest.entity.exam.QuestionInstance;
import com.eazytest.eazytest.exception.QuestionResourceNotFoundException;
import com.eazytest.eazytest.exception.ResourceNotFoundException;
import com.eazytest.eazytest.repository.exam.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionService implements QuestionServiceInterface {

    private QuestionRepository questionRepository;

    @Override
    public ReadQuestionResponseDto createSingleQuestionInstance(QuestionRequestDto questionRequestDto) {
        QuestionInstance questionInstance = QuestionInstance.builder()
                .examCategory(CategoryType.valueOf(questionRequestDto.getExamCategory()))
                .difficultyLevel(Difficulty.valueOf(questionRequestDto.getDifficultyLevel()))
                .title(questionRequestDto.getTitle())
                .option1(questionRequestDto.getOption1())
                .option2(questionRequestDto.getOption2())
                .option3(questionRequestDto.getOption3())
                .option4(questionRequestDto.getOption4())
                .rightAnswer(questionRequestDto.getRightAnswer())
                .isAvailable(true) //available to participant by default
                .build();

        questionRepository.save(questionInstance);

        return mapToReadQuestionResponseDto(String.format("Question with id: '%d' created successfully", questionInstance.getId()), questionInstance);
    }

    @Override
    public ReadQuestionResponseDto createMultipleQuestion(List<QuestionRequestDto> questionRequestDtoList) {
        List<QuestionInstance> questionInstanceList = questionRequestDtoList.stream().map(questionRequestDto -> questionRepository.save(
                QuestionInstance.builder()
                        .examCategory(CategoryType.valueOf(questionRequestDto.getExamCategory()))
                        .difficultyLevel(Difficulty.valueOf(questionRequestDto.getDifficultyLevel()))
                        .title(questionRequestDto.getTitle())
                        .option1(questionRequestDto.getOption1())
                        .option2(questionRequestDto.getOption2())
                        .option3(questionRequestDto.getOption3())
                        .option4(questionRequestDto.getOption4())
                        .rightAnswer(questionRequestDto.getRightAnswer())
                        .isAvailable(true)
                        .build()
        )).toList();

        return ReadQuestionResponseDto.builder()
                .message("Batch of questions successfully created")
                .suitableObjectResponseDto(questionInstanceList.stream().map(questionInstance -> QuestionResponseDto.builder()
                        .id(questionInstance.getId())
                        .title(questionInstance.getTitle())
                        .option1(questionInstance.getOption1())
                        .option2(questionInstance.getOption2())
                        .option3(questionInstance.getOption3())
                        .option4(questionInstance.getOption4())
                        .build()).collect(Collectors.toList()))
                .build();
    }

    @Override
    public ReadQuestionResponseDto fetchAllQuestions(int pageNo, int pageSize) {

        Page<QuestionInstance> questionInstances = convertListToPage(pageNo, pageSize);

        List<QuestionResponseDto> questionResponseDtoList = mapToQuestionResponseDto(questionInstances);

        String message = "Question list successfully returned according to page specifications";

        return mapToReadQuestionResponseDto(message, questionInstances, questionResponseDtoList);

//        List<QuestionInstance> questionInstanceList = questionInstances.getContent();
//
//        List<QuestionResponseDto> questionResponseDtoList = questionInstanceList.stream().map(questionInstance -> QuestionResponseDto.builder()
//                .id(questionInstance.getId())
//                .title(questionInstance.getTitle())
//                .option1(questionInstance.getOption1())
//                .option2(questionInstance.getOption2())
//                .option3(questionInstance.getOption3())
//                .option4(questionInstance.getOption4())
//                .build()).toList();

//        return ReadQuestionResponseDto.builder()
//                .message("Question list successfully returned according to page specifications")
//                .suitableObjectResponseDto(Collections.singletonList(PageableResponseDto.builder()
//                        .questionResponseDtoList(questionResponseDtoList)
//                        .pageNo(questionInstances.getNumber())
//                        .pageSize(questionInstances.getSize())
//                        .totalElements(questionInstances.getTotalElements())
//                        .totalPages(questionInstances.getTotalPages())
//                        .last(questionInstances.isLast())
//                        .build()))
//                .build();
    }

    @Override
    public ReadQuestionResponseDto findQuestionById(Long questionId) {
        QuestionInstance questionInstance = questionRepository.findById(questionId).orElseThrow(() -> new QuestionResourceNotFoundException(String.format("Question with id: '%d' not found", questionId)));

        return mapToReadQuestionResponseDto("Question requested successfully fetched", questionInstance);
    }

    @Override
    public ReadQuestionResponseDto findQuestionsByCategory(String category, int pageNo, int pageSize) {


        Page<QuestionInstance> questionInstances = convertListToPage(pageNo, pageSize);

        Page<QuestionInstance> filteredQuestions = questionInstances.stream().filter(questionInstance -> questionInstance.getExamCategory().toString().equals(category)).collect(Collectors.collectingAndThen(
                Collectors.toList(),
                questionInstanceList -> new PageImpl<>(questionInstanceList, PageRequest.of(pageNo, pageSize), questionInstanceList.size())
        ));

        if (filteredQuestions.isEmpty()) {
            throw new QuestionResourceNotFoundException("No question with the requested category found");
        }

        List<QuestionResponseDto> questionResponseDtoList = mapToQuestionResponseDto(filteredQuestions);

        return mapToReadQuestionResponseDto(String.format("Questions under the requested category: '%s' successfully fetched", category), filteredQuestions, questionResponseDtoList);
    }

    @Override
    public ReadQuestionResponseDto findQuestionBySearchQuery(String searchWord, int pageNo, int pageSize) {
        Page<QuestionInstance> questionInstances = convertListToPage(pageNo, pageSize);

        Page<QuestionInstance> filteredQuestionInstances = questionInstances.stream().filter(questionInstance -> questionInstance.getTitle().contains(searchWord)).collect(Collectors.collectingAndThen(Collectors.toList(), questionInstanceList -> new PageImpl<>(questionInstanceList, PageRequest.of(pageNo, pageSize), questionInstanceList.size())));

        if (filteredQuestionInstances.isEmpty()) {
            throw new QuestionResourceNotFoundException("The search word used does not match and question title in the repository");
        }

        List<QuestionResponseDto> questionResponseDtoList = mapToQuestionResponseDto(filteredQuestionInstances);

        return mapToReadQuestionResponseDto("Successfully retrieved matching list of questions with requested search word", filteredQuestionInstances, questionResponseDtoList);
    }

    @Override
    public ReadQuestionResponseDto updateQuestionInstance(Long questionId, QuestionRequestDto questionRequestDto) {
        QuestionInstance questionInstance = questionRepository.findById(questionId).orElseThrow(() -> new QuestionResourceNotFoundException(String.format("Question with id: '%d' not found", questionId)));

        questionInstance.setExamCategory(CategoryType.valueOf(questionRequestDto.getExamCategory()));
        questionInstance.setTitle(questionRequestDto.getTitle());
        questionInstance.setOption1(questionRequestDto.getOption1());
        questionInstance.setOption2(questionRequestDto.getOption2());
        questionInstance.setOption3(questionRequestDto.getOption3());
        questionInstance.setOption4(questionRequestDto.getOption4());
        questionInstance.setRightAnswer(questionInstance.getRightAnswer());
        questionInstance.setDifficultyLevel(Difficulty.valueOf(questionRequestDto.getDifficultyLevel()));

        questionRepository.save(questionInstance);

        return mapToReadQuestionResponseDto(String.format("Question with id: '%d' successfully updated", questionId), questionInstance);

    }

    @Override
    public ReadQuestionResponseDto deleteQuestionById(Long questionId) {
        QuestionInstance questionInstance = questionRepository.findById(questionId).orElseThrow(() -> new QuestionResourceNotFoundException(String.format("Question with id: '%d' not found", questionId)));

        questionInstance.setAvailable(false);
        questionRepository.save(questionInstance);

        return mapToReadQuestionResponseDto(String.format("Question with id: '%d' is now unavailable and removed from the questions presented to participants", questionId), questionInstance);
    }

    private Page<QuestionInstance> convertListToPage(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<QuestionInstance> questionInstances = questionRepository.findAll(pageable);


        if (questionInstances.isEmpty()) {
            throw new QuestionResourceNotFoundException("Question list is empty");
        }

        return questionInstances;
    }

    private List<QuestionResponseDto> mapToQuestionResponseDto(Page<QuestionInstance> questionInstances) {
        return questionInstances.getContent().stream().map(questionInstance -> QuestionResponseDto.builder()
                .id(questionInstance.getId())
                .title(questionInstance.getTitle())
                .option1(questionInstance.getOption1())
                .option2(questionInstance.getOption2())
                .option3(questionInstance.getOption3())
                .option4(questionInstance.getOption4())
                .build()).collect(Collectors.toList());
    }

    private ReadQuestionResponseDto mapToReadQuestionResponseDto(String message, Page<QuestionInstance> questionInstances, List<QuestionResponseDto> questionResponseDtoList) {
        return ReadQuestionResponseDto.builder()
                .message(message)
                .suitableObjectResponseDto(Collections.singletonList(PageableResponseDto.builder()
                        .questionResponseDtoList(questionResponseDtoList)
                        .pageNo(questionInstances.getNumber())
                        .pageSize(questionInstances.getSize())
                        .totalElements(questionInstances.getTotalElements())
                        .totalPages(questionInstances.getTotalPages())
                        .last(questionInstances.isLast())
                        .build()))
                .build();
    }

    private ReadQuestionResponseDto mapToReadQuestionResponseDto(String message, QuestionInstance questionInstance) {

        return ReadQuestionResponseDto.builder()
                .message(message)
                .suitableObjectResponseDto(Collections.singletonList(QuestionResponseDto.builder()
                                .id(questionInstance.getId())
                                .title(questionInstance.getTitle())
                                .option1(questionInstance.getOption1())
                                .option2(questionInstance.getOption2())
                                .option3(questionInstance.getOption3())
                                .option4(questionInstance.getOption4())
                        .build()))
                .build();
    }

}
