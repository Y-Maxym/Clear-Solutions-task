package com.spring.maxym.clearsolutionstask.service;

import com.spring.maxym.clearsolutionstask.entity.User;
import com.spring.maxym.clearsolutionstask.exception.IncorrectDateException;
import com.spring.maxym.clearsolutionstask.exception.UserNotFoundException;
import com.spring.maxym.clearsolutionstask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public List<User> getUsers(LocalDate startDate, LocalDate endDate) {
        if (isNull(startDate))
            startDate = LocalDate.of(1, 1, 1);
        if (isNull(endDate))
            endDate = LocalDate.now();

        if (startDate.isAfter(endDate))
            throw new IncorrectDateException("Start date cannot be after end date");
        else
            return findAllByBirthDateBetween(startDate, endDate);
    }

    private List<User> findAllByBirthDateBetween(LocalDate startDate, LocalDate endDate) {
        return userRepository.findAllByBirthDateBetween(startDate, endDate);
    }

    @Transactional
    public URI createUser(User user) {
        userRepository.save(user);
        return generateURI(user.getId());
    }

    @Transactional
    public void updateUser(User userToUpdate) {
        userRepository.save(userToUpdate);
    }

    @Transactional
    public void deleteUserById(Long id) {
        boolean existsById = userRepository.existsById(id);
        if (!existsById) throw new UserNotFoundException();

        userRepository.deleteById(id);
    }

    private URI generateURI(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
