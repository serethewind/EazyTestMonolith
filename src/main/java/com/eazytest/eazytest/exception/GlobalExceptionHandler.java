package com.eazytest.eazytest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {

        return new ResponseEntity<>(mapToErrorDetails(exception, "USER_NOT_FOUND", webRequest), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(QuestionResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleQuestionResourceNotFoundException(QuestionResourceNotFoundException exception, WebRequest webRequest){

        return new ResponseEntity<>(mapToErrorDetails(exception, "QUESTION_RESOURCE_NOT_FOUND", webRequest), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExamResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleExamResourceNotFoundException(ExamResourceNotFoundException exception, WebRequest webRequest){
        return new ResponseEntity<>(mapToErrorDetails(exception, "EXAM_RESOURCE_NOT_FOUND", webRequest), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> handleBadRequestException(BadRequestException exception, WebRequest webRequest){

        return new ResponseEntity<>(mapToErrorDetails(exception, "BAD_REQUEST", webRequest), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> handleEmailAlreadyExistException(EmailAlreadyExistException exception, WebRequest webRequest){

        return new ResponseEntity<>(mapToErrorDetails(exception, "USER_EMAIL_ALREADY_EXISTS", webRequest), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest){

        return new ResponseEntity<>(mapToErrorDetails(exception, "INTERNAL_SERVER_ERROR", webRequest), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private ErrorDetails mapToErrorDetails(Exception exception, String message, WebRequest webRequest){

        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .path(webRequest.getDescription(false))
                .errorCode(message)
                .build();
    }
}
