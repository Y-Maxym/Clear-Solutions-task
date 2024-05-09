package com.spring.maxym.clearsolutionstask.error;

import java.util.List;

public record ErrorEntity(
        String message,
        List<FieldErrorEntity> errors,
        long timestamp
) {
}