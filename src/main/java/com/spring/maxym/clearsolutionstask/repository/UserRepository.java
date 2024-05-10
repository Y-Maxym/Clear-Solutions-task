package com.spring.maxym.clearsolutionstask.repository;

import com.spring.maxym.clearsolutionstask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByBirthDateBetween(@NonNull LocalDate startDate, @NonNull LocalDate endDate);
}
