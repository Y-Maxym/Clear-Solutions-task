package com.spring.maxym.clearsolutionstask.utils;

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
}
