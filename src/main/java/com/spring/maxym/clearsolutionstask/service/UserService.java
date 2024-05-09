package com.spring.maxym.clearsolutionstask.service;

import com.spring.maxym.clearsolutionstask.dto.UserCreateDto;
import com.spring.maxym.clearsolutionstask.dto.UserResponseDto;
import com.spring.maxym.clearsolutionstask.dto.UserUpdateDto;
import com.spring.maxym.clearsolutionstask.entity.User;
import com.spring.maxym.clearsolutionstask.exception.IncorrectDateException;
import com.spring.maxym.clearsolutionstask.exception.UserNotFoundException;
import com.spring.maxym.clearsolutionstask.mapper.UserMapper;
import com.spring.maxym.clearsolutionstask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.isNull;

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

    public List<UserResponseDto> getUsers(LocalDate startDate, LocalDate endDate) {
        if (isNull(startDate))
            startDate = LocalDate.of(1, 1, 1);
        if (isNull(endDate))
            endDate = LocalDate.now();

        if (startDate.isAfter(endDate))
            throw new IncorrectDateException("Start date cannot be after end date");
        else
            return findAllByBirthDateBetween(startDate, endDate);
    }

    private List<UserResponseDto> findAllByBirthDateBetween(LocalDate startDate, LocalDate endDate) {
        return userMapper.toListDto(userRepository.findAllByBirthDateBetween(startDate, endDate));
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

    @Transactional
    public void deleteUserById(Long id) {
        boolean existsById = userRepository.existsById(id);
        if (!existsById) throw new UserNotFoundException();

        userRepository.deleteById(id);
    }
}
