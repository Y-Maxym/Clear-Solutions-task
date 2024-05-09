package com.spring.maxym.clearsolutionstask.service;

import com.spring.maxym.clearsolutionstask.dto.UserCreateDto;
import com.spring.maxym.clearsolutionstask.dto.UserResponseDto;
import com.spring.maxym.clearsolutionstask.dto.UserUpdateDto;
import com.spring.maxym.clearsolutionstask.entity.User;
import com.spring.maxym.clearsolutionstask.exception.UserNotFoundException;
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

    public UserResponseDto getUserById(Long id) {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new));
    }

    @Transactional
    public void createUser(UserCreateDto dto) {
        User user = userMapper.toEntityFromCreateDto(dto);
        userRepository.save(user);
    }

    @Transactional
    public void updateUserById(Long id, UserUpdateDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        userMapper.updateUserFromDTO(dto, user);
        userRepository.save(user);
    }
}
