package com.spring.maxym.clearsolutionstask.rest;


import com.spring.maxym.clearsolutionstask.dto.UserCreateDto;
import com.spring.maxym.clearsolutionstask.exception.UserCreationException;
import com.spring.maxym.clearsolutionstask.service.BindingResultService;
import com.spring.maxym.clearsolutionstask.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestControllerV1 {

    private final UserService userService;
    private final BindingResultService bindingResultService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserCreateDto dto,
                                        BindingResult bindingResult) {

        bindingResultService.handle(bindingResult, UserCreationException::new);
        userService.createUser(dto);
        return ResponseEntity.ok().build();
    }
}
