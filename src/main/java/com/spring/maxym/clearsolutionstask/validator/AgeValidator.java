package com.spring.maxym.clearsolutionstask.validator;

import com.spring.maxym.clearsolutionstask.annotation.ValidAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.Period;

import static java.util.Objects.isNull;

@Slf4j
public class AgeValidator implements ConstraintValidator<ValidAge, LocalDate> {

    @Value("${user.minAge}")
    private Integer minAge = 18;

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        log.info("Trying to validate age birth date {}", birthDate);
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
