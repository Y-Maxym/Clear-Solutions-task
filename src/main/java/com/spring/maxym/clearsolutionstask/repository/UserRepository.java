package com.spring.maxym.clearsolutionstask.repository;

import com.spring.maxym.clearsolutionstask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsById(@NonNull Long id);
}
