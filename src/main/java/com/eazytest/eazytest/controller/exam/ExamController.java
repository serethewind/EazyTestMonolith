package com.eazytest.eazytest.controller.exam;

import com.eazytest.eazytest.dto.exam.ActivateSessionDto;
import com.eazytest.eazytest.dto.exam.ExamRequestDto;
import com.eazytest.eazytest.dto.exam.ExamUpdateRequestDto;
import com.eazytest.eazytest.dto.exam.SessionWithGeneratedQuestionsDto;
import com.eazytest.eazytest.dto.general.ReadResponseDto;
import com.eazytest.eazytest.dto.question.RecordDto;
import com.eazytest.eazytest.service.exam.examsession.ExamService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/eazytest/exam-session")
@RestController
@AllArgsConstructor
public class ExamController {

    private ExamService examService;

    @PostMapping
    public ResponseEntity<ReadResponseDto> createExamSession(@RequestBody ExamRequestDto examRequestDto){
        return new ResponseEntity<>(examService.createExamSession(examRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/batch-creation")
    public ResponseEntity<ReadResponseDto> createExamSessionInBatch(@RequestBody List<ExamRequestDto> examRequestDtoList){
        return new ResponseEntity<>(examService.createExamSessionInBatch(examRequestDtoList), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ReadResponseDto> updateExamSession(@PathVariable("id") String sessionId, @RequestBody ExamUpdateRequestDto examRequestDto){
        return new ResponseEntity<>(examService.updateExamSession(sessionId, examRequestDto), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ReadResponseDto> fetchExamSession(@PathVariable("id") String sessionId){
        return new ResponseEntity<>(examService.fetchExamSessionById(sessionId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ReadResponseDto> fetchAllExamSession(){
        return new ResponseEntity<>(examService.fetchAllExamSession(), HttpStatus.OK);
    }

    @GetMapping("examinerId")
    public ResponseEntity<ReadResponseDto> fetchAllExamSessionByExaminer(@RequestParam("examinerId")String examinerId){
        return new ResponseEntity<>(examService.fetchAllExamSessionByExaminer(examinerId), HttpStatus.OK);
    }

    @GetMapping("examinerId/single-exam-session")
    public ResponseEntity<ReadResponseDto> fetchSingleExamSessionByExaminer(@RequestParam("examinerId")String examinerId, @RequestParam("sessionId")String sessionId){
        return new ResponseEntity<>(examService.fetchExamSessionByExaminer(examinerId, sessionId), HttpStatus.OK);
    }

    @PostMapping("initiate-examSession")
    public ResponseEntity<ReadResponseDto> initiateExamSessionForParticipants(@RequestBody ActivateSessionDto activateSessionDto){
        return new ResponseEntity<>(examService.initiateExamSessionForParticipants(activateSessionDto), HttpStatus.OK);
    }

    @PostMapping("end-examSession")
    public ResponseEntity<ReadResponseDto> endExamSessionForParticipants(@RequestBody ActivateSessionDto activateSessionDto){
        return new ResponseEntity<>(examService.endExamSessionForParticipants(activateSessionDto), HttpStatus.OK);
    }

    @PostMapping("generate-questions/{id}")
    public ResponseEntity<SessionWithGeneratedQuestionsDto> generateQuestionsForExamSession(@PathVariable("id") String sessionId, @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo, @RequestParam(value = "pageSize", defaultValue = "8", required = false) int pageSize){
        return new ResponseEntity<>(examService.generateQuestionsForExamSession(sessionId, pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("fetch-records")
    public ResponseEntity<List<RecordDto>> retrieveParticipantsAndScoreBySessionId(@RequestParam String sessionId){
        return new ResponseEntity<>(examService.fetchParticipantsAndScore(sessionId), HttpStatus.OK);
    }
}
