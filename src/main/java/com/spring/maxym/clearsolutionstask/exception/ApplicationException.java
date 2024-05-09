package com.spring.maxym.clearsolutionstask.exception;

import com.spring.maxym.clearsolutionstask.error.FieldErrorEntity;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class ApplicationException extends RuntimeException {

    private final List<FieldErrorEntity> errors;

    public ApplicationException(String message, List<FieldErrorEntity> errors) {
        super(message);
        this.errors = errors;
    }
}