package com.spring.maxym.clearsolutionstask.rest;


import com.spring.maxym.clearsolutionstask.dto.UserCreateDto;
import com.spring.maxym.clearsolutionstask.dto.UserResponseDto;
import com.spring.maxym.clearsolutionstask.dto.UserUpdateDto;
import com.spring.maxym.clearsolutionstask.exception.UserCreationException;
import com.spring.maxym.clearsolutionstask.exception.UserUpdateException;
import com.spring.maxym.clearsolutionstask.service.BindingResultService;
import com.spring.maxym.clearsolutionstask.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestControllerV1 {

    private final UserService userService;
    private final BindingResultService bindingResultService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        UserResponseDto userDto = userService.getUserById(id);
        return ResponseEntity.status(200).body(userDto);
    }

    @GetMapping
    public ResponseEntity<?> getUsers(@RequestParam(value = "from", required = false)
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                      @RequestParam(value = "to", required = false)
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<UserResponseDto> users = userService.getUsers(startDate, endDate);
        return ResponseEntity.status(200).body(users);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserCreateDto dto,
                                        BindingResult bindingResult) {

        bindingResultService.handle(bindingResult, UserCreationException::new);
        userService.createUser(dto);
        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable("id") Long id,
                                            @RequestBody @Valid UserUpdateDto dto,
                                            BindingResult bindingResult) {

        bindingResultService.handle(bindingResult, UserUpdateException::new);
        userService.updateUserById(id, dto);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(204).build();
    }
}
