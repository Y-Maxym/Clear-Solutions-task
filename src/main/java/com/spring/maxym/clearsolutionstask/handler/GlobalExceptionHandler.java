package com.spring.maxym.clearsolutionstask.handler;

import com.spring.maxym.clearsolutionstask.error.ErrorEntity;
import com.spring.maxym.clearsolutionstask.error.SimpleErrorEntity;
import com.spring.maxym.clearsolutionstask.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserCreationException.class, UserUpdateException.class})
    public ResponseEntity<?> handleException(ApplicationException exception) {
        log.error("Exception was thrown: {}", exception.getMessage());
        ErrorEntity error = new ErrorEntity(exception.getMessage(), exception.getErrors(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            IncorrectDateException.class})
    public ResponseEntity<?> handleException(Exception exception) {
        log.error("Exception was thrown: {}", exception.getMessage());
        SimpleErrorEntity error = new SimpleErrorEntity(exception.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleException(UserNotFoundException exception) {
        log.error("Exception was thrown: {}", exception.getMessage());
        SimpleErrorEntity error = new SimpleErrorEntity(exception.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
