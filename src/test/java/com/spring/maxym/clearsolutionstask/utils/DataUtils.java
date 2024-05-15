package com.spring.maxym.clearsolutionstask.utils;

import com.spring.maxym.clearsolutionstask.dto.UserCreateDto;
import com.spring.maxym.clearsolutionstask.dto.UserResponseDto;
import com.spring.maxym.clearsolutionstask.dto.UserUpdateDto;
import com.spring.maxym.clearsolutionstask.entity.User;

import java.time.LocalDate;

public class DataUtils {

    public static User getJohnDoeTransient() {
        return User.builder()
                .email("john.doe@example.com")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 1, 1))
                .address("address1")
                .phoneNumber("phone1")
                .build();
    }

    public static User getMikeSmithTransient() {
        return User.builder()
                .email("mike.smith@example.com")
                .firstName("Mike")
                .lastName("Smith")
                .birthDate(LocalDate.of(1994, 1, 1))
                .address("address2")
                .phoneNumber("phone2")
                .build();
    }

    public static User getFrankJonesTransient() {
        return User.builder()
                .email("frank.jones@example.com")
                .firstName("Frank")
                .lastName("Jones")
                .birthDate(LocalDate.of(1995, 1, 1))
                .address("address3")
                .phoneNumber("phone3")
                .build();
    }

    public static User getJohnDoePersisted() {
        return User.builder()
                .id(1L)
                .email("john.doe@example.com")
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 1, 1))
                .address("address1")
                .phoneNumber("phone1")
                .build();
    }

    public static User getMikeSmithPersisted() {
        return User.builder()
                .id(2L)
                .email("mike.smith@example.com")
                .firstName("Mike")
                .lastName("Smith")
                .birthDate(LocalDate.of(1994, 1, 1))
                .address("address2")
                .phoneNumber("phone2")
                .build();
    }

    public static User getFrankJonesPersisted() {
        return User.builder()
                .id(3L)
                .email("frank.jones@example.com")
                .firstName("Frank")
                .lastName("Jones")
                .birthDate(LocalDate.of(1995, 1, 1))
                .address("address3")
                .phoneNumber("phone3")
                .build();
    }

    public static UserCreateDto getJohnDoeDtoToCreateTransient() {
        return new UserCreateDto(
                "john.doe@gmail.com",
                "John",
                "Doe",
                LocalDate.of(1990, 1, 1),
                "address1",
                "phone1");
    }

    public static UserUpdateDto getJohnDoeDtoToUpdateTransient() {
        return new UserUpdateDto(
                "updated@gmail.com",
                "Updated",
                "Updated",
                null,
                null,
                null);
    }

    public static UserResponseDto getJohnDoeResponseDto() {
        return new UserResponseDto(
                1L,
                "john.doe@example.com",
                "John",
                "Doe",
                LocalDate.of(1990, 1, 1),
                "address1",
                "phone1");
    }


    public static UserResponseDto getMikeSmithResponseDto() {
        return new UserResponseDto(
                2L,
                "mike.smith@example.com",
                "Mike",
                "Smith",
                LocalDate.of(1994, 1, 1),
                "address2",
                "phone2");
    }

    public static UserResponseDto getFrankJonesResponseDto() {
        return new UserResponseDto(
                3L,
                "frank.jones@example.com",
                "Frank",
                "Jones",
                LocalDate.of(1995, 1, 1),
                "address3",
                "phone3");
    }

    public static UserCreateDto getJohnDoeDtoToCreateIncorrect() {
        return new UserCreateDto(
                "incorrect@mail",
                "John",
                "Doe",
                LocalDate.of(1990, 1, 1),
                "address1",
                "phone1");
    }

    public static UserUpdateDto getJohnDoeDtoToUpdateIncorrect() {
        return new UserUpdateDto(
                "updated@incorrect.com",
                "Updated",
                "Updated",
                null,
                null,
                null);
    }
}
