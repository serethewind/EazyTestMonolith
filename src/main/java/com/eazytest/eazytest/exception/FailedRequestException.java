package com.eazytest.eazytest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FailedRequestException extends RuntimeException{

    public FailedRequestException(String message){
        super(message);
    }
}
