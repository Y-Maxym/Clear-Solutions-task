package com.spring.maxym.clearsolutionstask.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.maxym.clearsolutionstask.annotation.ValidAge;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserCreateDto(

        @NotEmpty(message = "Email should not be empty")
        @Email(regexp = ".+@(gmail.com|.+.ua)", message = "Invalid email format (use gmail.com or ua domain)")
        String email,

        @NotEmpty(message = "First name should not be empty")
        String firstName,

        @NotEmpty(message = "Last name should not be empty")
        String lastName,

        @NotNull(message = "Birth date should not be empty")
        @Past(message = "Birth date must be earlier than current date")
        @ValidAge
        LocalDate birthDate,

        String address,

        String phoneNumber
) {
}
