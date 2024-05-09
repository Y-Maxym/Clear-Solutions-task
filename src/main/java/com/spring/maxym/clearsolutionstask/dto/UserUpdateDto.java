package com.spring.maxym.clearsolutionstask.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.maxym.clearsolutionstask.annotation.ValidAge;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserUpdateDto(

        @Email(regexp = ".+@(gmail.com|.+.ua)", message = "Invalid email format (use gmail.com or ua domain)")
        String email,

        String firstName,

        String lastName,

        @Past(message = "Birth date must be earlier than current date")
        @ValidAge
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate birthDate,

        String address,

        String phoneNumber
) {
}
