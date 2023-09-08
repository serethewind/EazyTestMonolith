package com.eazytest.eazytest.controller.Exam;

import com.eazytest.eazytest.dto.Exam.ActivateSessionDto;
import com.eazytest.eazytest.dto.Exam.ExamRequestDto;
import com.eazytest.eazytest.dto.general.ResponseDto;
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
    ResponseEntity<ResponseDto> createExamSession(@RequestBody ExamRequestDto examRequestDto){
        return new ResponseEntity<>(examService.createExamSession(examRequestDto), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    ResponseEntity<ResponseDto> updateExamSession(@PathVariable("id") String sessionId, @RequestBody ExamRequestDto examRequestDto){
        return new ResponseEntity<>(examService.updateExamSession(sessionId, examRequestDto), HttpStatus.OK);
    }

    @GetMapping("{id}")
    ResponseEntity<ResponseDto> fetchExamSession(@PathVariable("id") String sessionId){
        return new ResponseEntity<>(examService.fetchExamSessionById(sessionId), HttpStatus.OK);
    }

    @GetMapping("{id}")
    ResponseEntity<List<ResponseDto>> fetchAllExamSessionByExaminer(@RequestParam("examinerId")String examinerId){
        return new ResponseEntity<>(examService.fetchAllExamSessionByExaminer(examinerId), HttpStatus.OK);
    }

    @PostMapping("initiate-examsession")
    ResponseEntity<ResponseDto> initiateExamSessionForParticipants(@RequestBody ActivateSessionDto activateSessionDto){
        return new ResponseEntity<>(examService.initiateExamSessionForParticipants(activateSessionDto), HttpStatus.OK);
    }

    @PostMapping("end-examsession")
    ResponseEntity<ResponseDto> endExamSessionForParticipants(@RequestBody ActivateSessionDto activateSessionDto){
        return new ResponseEntity<>(examService.endExamSessionForParticipants(activateSessionDto), HttpStatus.OK);
    }
}
