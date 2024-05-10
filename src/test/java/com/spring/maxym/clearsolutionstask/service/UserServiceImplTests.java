package com.spring.maxym.clearsolutionstask.service;

import com.spring.maxym.clearsolutionstask.entity.User;
import com.spring.maxym.clearsolutionstask.exception.IncorrectDateException;
import com.spring.maxym.clearsolutionstask.exception.UserNotFoundException;
import com.spring.maxym.clearsolutionstask.repository.UserRepository;
import com.spring.maxym.clearsolutionstask.utils.DataUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Test create user functionality")
    public void givenUserCreateDto_whenCreateUser_thenRepositoryIsCalled() {
        //given
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        User userToSave = DataUtils.getJohnDoePersisted();
        BDDMockito.given(userRepository.save(any(User.class)))
                .willReturn(userToSave);
        //when
        URI uri = userService.createUser(userToSave);
        //then
        verify(userRepository, times(1)).save(any(User.class));
        assertThat(uri).isNotNull();
        assertThat(uri.getPath()).isEqualTo("/%s".formatted(userToSave.getId()));
    }

    @Test
    @DisplayName("Test update user functionality")
    public void givenUserToUpdate_whenUpdateUser_thenRepositoryIsCalled() {
        //given
        User userToUpdate = DataUtils.getJohnDoePersisted();
        BDDMockito.given(userRepository.save(any(User.class)))
                .willReturn(userToUpdate);
        //when
        userService.updateUser(userToUpdate);
        //then
        assertThat(userToUpdate).isNotNull();
        verify(userRepository, times(1)).save(userToUpdate);

    }

    @Test
    @DisplayName("Test get user by id functionality")
    public void givenId_whenGetUserById_thenUserIsReturned() {
        //given
        BDDMockito.given(userRepository.findById(anyLong()))
                .willReturn(Optional.of(DataUtils.getJohnDoePersisted()));
        //when
        User obtainedUser = userService.getUserById(1L);
        //then
        assertThat(obtainedUser).isNotNull();
    }

    @Test
    @DisplayName("Test get user by incorrect id functionality")
    public void givenIncorrectId_whenGetUserById_thenExceptionIsThrown() {
        //given
        BDDMockito.given(userRepository.findById(anyLong()))
                .willReturn(Optional.empty());
        //when
        //then
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    @DisplayName("Test get all users functionality")
    public void givenThreeUsers_whenGetUsers_thenAllUsersAreReturned() {
        //given
        User user1 = DataUtils.getJohnDoeTransient();
        User user2 = DataUtils.getMikeSmithTransient();
        User user3 = DataUtils.getFrankJonesTransient();

        List<User> users = List.of(user1, user2, user3);
        BDDMockito.given(userRepository.findAllByBirthDateBetween(any(LocalDate.class), any(LocalDate.class)))
                .willReturn(users);
        //when
        List<User> obtainedUsers = userService.getUsers(null, null);
        //then
        assertThat(CollectionUtils.isEmpty(obtainedUsers)).isFalse();
        assertThat(obtainedUsers.size()).isEqualTo(3);
        verify(userRepository, times(1)).findAllByBirthDateBetween(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    @DisplayName("Test get all users exception functionality")
    public void givenThreeUsers_whenGetUsers_thenExceptionIsThrown() {
        //given
        LocalDate startDate = LocalDate.of(2021, 1, 1);
        LocalDate endDate = LocalDate.of(2020, 12, 31);
        //when
        IncorrectDateException exception = assertThrows(IncorrectDateException.class, () -> userService.getUsers(startDate, endDate));
        //then
        assertThat(exception.getMessage()).isEqualTo("Start date cannot be after end date");
        verify(userRepository, never()).findAllByBirthDateBetween(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    @DisplayName("Test delete user by id functionality")
    public void givenId_whenDeleteUser_thenRepositoryDeleteMethodIsCalled() {
        //given
        BDDMockito.given(userRepository.existsById(anyLong()))
                .willReturn(true);
        //when
        userService.deleteUserById(1L);
        //then
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Test delete user by incorrect functionality")
    public void givenIncorrectId_whenDeleteUser_thenExceptionIsThrown() {
        //given
        BDDMockito.given(userRepository.existsById(anyLong()))
                .willReturn(false);
        //when
        //then
        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(1L));
        verify(userRepository, never()).deleteById(1L);
    }
}

