package com.spring.maxym.clearsolutionstask.service;

import com.spring.maxym.clearsolutionstask.dto.UserCreateDto;
import com.spring.maxym.clearsolutionstask.entity.User;
import com.spring.maxym.clearsolutionstask.mapper.UserMapper;
import com.spring.maxym.clearsolutionstask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public void createUser(UserCreateDto dto) {
        User user = userMapper.toEntity(dto);
        userRepository.save(user);
    }
}
