package com.eazytest.eazytest.controller.exam;

import com.eazytest.eazytest.dto.general.ReadQuestionResponseDto;
import com.eazytest.eazytest.dto.question.QuestionRequestDto;
import com.eazytest.eazytest.service.exam.question.QuestionServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/eazytest/question-session")
@RestController
@AllArgsConstructor
public class QuestionController {

    private QuestionServiceInterface questionService;

    @PostMapping
    ResponseEntity<ReadQuestionResponseDto> createQuestion(@RequestBody QuestionRequestDto questionRequestDto) {
        return new ResponseEntity<>(questionService.createSingleQuestionInstance(questionRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("batch")
    ResponseEntity<ReadQuestionResponseDto> createMultipleQuestions(@RequestBody List<QuestionRequestDto> questionRequestDtoList){
        return new ResponseEntity<>(questionService.createMultipleQuestion(questionRequestDtoList), HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<ReadQuestionResponseDto> findAllQuestions(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return new ResponseEntity<>(questionService.fetchAllQuestions(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("{id}")
    ResponseEntity<ReadQuestionResponseDto> findQuestionById(@PathVariable("id") Long questionId) {
        return new ResponseEntity<>(questionService.findQuestionById(questionId), HttpStatus.OK);
    }

    @GetMapping("category-filter")
    ResponseEntity<ReadQuestionResponseDto> findQuestionsByCategory(@RequestParam("category") String category, @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "4", required = false) int pageSize
    ) {
        return new ResponseEntity<>(questionService.findQuestionsByCategory(category, pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("search")
    ResponseEntity<ReadQuestionResponseDto> findQuestionsBySearchQuery(@RequestParam("search-word")String searchWord, @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "8", required = false) int pageSize){
        return new ResponseEntity<>(questionService.findQuestionBySearchQuery(searchWord, pageNo, pageSize), HttpStatus.OK);
    }

    @PutMapping("{id}/update")
    ResponseEntity<ReadQuestionResponseDto> updateQuestion(@RequestBody QuestionRequestDto questionRequestDto, @PathVariable("id") Long questionId) {
        return new ResponseEntity<>(questionService.updateQuestionInstance(questionId, questionRequestDto), HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    ResponseEntity<ReadQuestionResponseDto> deleteQuestionById(@PathVariable("id") Long questionId){
        return new ResponseEntity<>(questionService.deleteQuestionById(questionId), HttpStatus.OK);
    }

}
