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
        return new ResponseEntity<>(
                ErrorDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .message(exception.getMessage())
                        .path(webRequest.getDescription(false))
                        .errorCode("USER_NOT_FOUND")
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> handleEmailAlreadyExistException(EmailAlreadyExistException exception, WebRequest webRequest){
        return new ResponseEntity<>(
                ErrorDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .message(exception.getMessage())
                        .path(webRequest.getDescription(false))
                        .errorCode("USER_EMAIL_ALREADY_EXISTS")
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest){
        return new ResponseEntity<>(
                ErrorDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .message(exception.getMessage())
                        .path(webRequest.getDescription(false))
                        .errorCode("INTERNAL_SERVER_ERROR")
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> handleBadRequestException(BadRequestException exception, WebRequest webRequest){
        return new ResponseEntity<>(
                ErrorDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .message(exception.getMessage())
                        .path(webRequest.getDescription(false))
                        .errorCode("BAD_REQUEST")
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}
