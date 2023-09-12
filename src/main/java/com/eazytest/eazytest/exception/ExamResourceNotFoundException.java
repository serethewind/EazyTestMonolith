package com.eazytest.eazytest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExamResourceNotFoundException extends RuntimeException {

    public ExamResourceNotFoundException(String message){
        super(message);
    }
}
