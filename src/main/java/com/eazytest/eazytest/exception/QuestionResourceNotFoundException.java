package com.eazytest.eazytest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class QuestionResourceNotFoundException extends RuntimeException{

    public QuestionResourceNotFoundException(String message){
        super(message);
    }
}
