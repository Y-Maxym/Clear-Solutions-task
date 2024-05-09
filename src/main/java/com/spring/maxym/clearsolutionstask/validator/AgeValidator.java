package com.spring.maxym.clearsolutionstask.validator;

import com.spring.maxym.clearsolutionstask.annotation.ValidAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.Period;

import static java.util.Objects.isNull;

public class AgeValidator implements ConstraintValidator<ValidAge, LocalDate> {

    @Value("${user.minAge}")
    private Integer minAge;

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        if (isNull(birthDate)) return true;

        LocalDate today = LocalDate.now();
        Period period = Period.between(birthDate, today);
        boolean isValid = period.getYears() >= minAge;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("The user must be over %s years old".formatted(minAge))
                .addConstraintViolation();

        return isValid;
    }
}
