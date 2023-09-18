package com.eazytest.eazytest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SubscriptionTypeException extends RuntimeException{

    public SubscriptionTypeException(String message){
        super(message);
    }
}
