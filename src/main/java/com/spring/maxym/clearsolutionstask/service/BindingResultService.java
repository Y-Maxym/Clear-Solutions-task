package com.spring.maxym.clearsolutionstask.service;

import com.spring.maxym.clearsolutionstask.error.FieldErrorEntity;
import com.spring.maxym.clearsolutionstask.exception.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.function.Function;

@Service
public class BindingResultService {

    public void handle(BindingResult bindingResult, Function<List<FieldErrorEntity>, ? extends ApplicationException> exception) {

        if (bindingResult.hasErrors()) {
            List<FieldErrorEntity> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldErrorEntity::new)
                    .toList();

            throw exception.apply(errors);
        }
    }
}
