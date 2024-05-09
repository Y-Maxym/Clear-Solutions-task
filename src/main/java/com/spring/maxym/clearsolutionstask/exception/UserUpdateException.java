package com.spring.maxym.clearsolutionstask.exception;

import com.spring.maxym.clearsolutionstask.error.FieldErrorEntity;

import java.util.List;

public class UserUpdateException extends ApplicationException {

    public UserUpdateException(List<FieldErrorEntity> errors) {
        super("User update error", errors);
    }
}
