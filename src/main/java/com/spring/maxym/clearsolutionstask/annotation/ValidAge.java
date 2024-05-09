package com.spring.maxym.clearsolutionstask.annotation;

import com.spring.maxym.clearsolutionstask.validator.AgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AgeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAge {

    String message() default "The user must be over the specified age";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}