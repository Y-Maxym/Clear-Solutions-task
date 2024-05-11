package com.spring.maxym.clearsolutionstask.service;

import com.spring.maxym.clearsolutionstask.error.FieldErrorEntity;
import com.spring.maxym.clearsolutionstask.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.function.Function;

@Service
@Slf4j
public class BindingResultService {

    public void handle(BindingResult bindingResult, Function<List<FieldErrorEntity>, ? extends ApplicationException> exception) {
        log.info("Trying to handle BindingResult {}", bindingResult);

        if (bindingResult.hasErrors()) {
            List<FieldErrorEntity> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldErrorEntity::new)
                    .toList();
            log.warn("Field validation error, fields do not meet requirements: \n{}", errors);
            throw exception.apply(errors);
        }
    }
}
