package com.spring.maxym.clearsolutionstask.exception;

import com.spring.maxym.clearsolutionstask.error.FieldErrorEntity;

import java.util.List;

public class UserCreationException extends ApplicationException {

    public UserCreationException(List<FieldErrorEntity> errors) {
        super("User creation error", errors);
    }
}
