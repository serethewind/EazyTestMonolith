package com.eazytest.eazytest.controller.exam;

import com.eazytest.eazytest.dto.exam.SessionWithGeneratedQuestionsDto;
import com.eazytest.eazytest.dto.exam.SubmitScoreDto;
import com.eazytest.eazytest.dto.exam.TakeExamSessionDto;
import com.eazytest.eazytest.dto.general.ReadAnswerResponseDto;
import com.eazytest.eazytest.dto.general.ReadResponseDto;
import com.eazytest.eazytest.service.exam.examsession.ExamService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/eazytest/participant-session")
@AllArgsConstructor
public class ParticipantController {

    private ExamService examService;

    @GetMapping("active-exam-sessions")
    public ResponseEntity<ReadResponseDto> viewActiveExamSession(){
        return new ResponseEntity<>(examService.fetchActiveExamSessions(), HttpStatus.OK);
    }

    @PostMapping("take-exam-session")
    public ResponseEntity<SessionWithGeneratedQuestionsDto> takeActiveExamSession(@RequestBody TakeExamSessionDto takeExamSessionDto, @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "3", required = false)int pageSize){
        return new ResponseEntity<>(examService.viewExamSessionForParticipant(takeExamSessionDto, pageNo, pageSize), HttpStatus.OK);
    }

    @PostMapping("submit-response")
    public ResponseEntity<ReadAnswerResponseDto> submitResponseForExamSession(@RequestBody SubmitScoreDto submitScoreDto){
        return new ResponseEntity<>(examService.gradeResponseFromParticipant(submitScoreDto), HttpStatus.OK);
    }
}
