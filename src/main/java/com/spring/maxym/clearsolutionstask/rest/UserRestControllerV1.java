package com.spring.maxym.clearsolutionstask.rest;


import com.spring.maxym.clearsolutionstask.dto.UserCreateDto;
import com.spring.maxym.clearsolutionstask.dto.UserResponseDto;
import com.spring.maxym.clearsolutionstask.dto.UserUpdateDto;
import com.spring.maxym.clearsolutionstask.entity.User;
import com.spring.maxym.clearsolutionstask.exception.UserCreationException;
import com.spring.maxym.clearsolutionstask.exception.UserUpdateException;
import com.spring.maxym.clearsolutionstask.mapper.UserMapper;
import com.spring.maxym.clearsolutionstask.service.BindingResultService;
import com.spring.maxym.clearsolutionstask.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestControllerV1 {

    private final UserService userService;
    private final UserMapper userMapper;
    private final BindingResultService bindingResultService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {

        User obtainedUser = userService.getUserById(id);
        UserResponseDto userDto = userMapper.toDto(obtainedUser);

        return ResponseEntity.status(200).body(userDto);
    }

    @GetMapping
    public ResponseEntity<?> getUsers(@RequestParam(value = "from", required = false)
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                      @RequestParam(value = "to", required = false)
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<User> obtainedUsers = userService.getUsers(startDate, endDate);
        List<UserResponseDto> listDto = userMapper.toListDto(obtainedUsers);

        return ResponseEntity.status(200).body(listDto);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserCreateDto dto,
                                        BindingResult bindingResult) {

        bindingResultService.handle(bindingResult, UserCreationException::new);

        User userToCreate = userMapper.toEntityFromCreateDto(dto);
        URI location = userService.createUser(userToCreate);
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable("id") Long id,
                                            @RequestBody @Valid UserUpdateDto dto,
                                            BindingResult bindingResult) {

        bindingResultService.handle(bindingResult, UserUpdateException::new);

        User obtainedUser = userService.getUserById(id);
        userMapper.updateUserFromDTO(dto, obtainedUser);
        userService.updateUser(obtainedUser);

        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(204).build();
    }
}
