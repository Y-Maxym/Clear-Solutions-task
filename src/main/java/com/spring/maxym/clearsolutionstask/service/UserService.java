package com.spring.maxym.clearsolutionstask.service;

import com.spring.maxym.clearsolutionstask.entity.User;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

public interface UserService {

    User getUserById(Long id);

    List<User> getUsers(LocalDate startDate, LocalDate endDate);

    URI createUser(User userToCreate);

    void updateUser(User userToUpdate);

    void deleteUserById(Long id);
}
