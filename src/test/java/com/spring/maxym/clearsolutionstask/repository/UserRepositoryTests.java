package com.spring.maxym.clearsolutionstask.repository;

import com.spring.maxym.clearsolutionstask.entity.User;
import com.spring.maxym.clearsolutionstask.utils.DataUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Test save user functionality")
    public void givenUser_whenSaveUser_thenUserIsCreated() {
        //given
        User user = DataUtils.getJohnDoeTransient();
        //when
        userRepository.save(user);
        //then
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test update user functionality")
    public void givenUserToUpdate_whenSave_thenEmailIsChanged() {
        //given
        String updatedEmail = "updated@email.com";
        User userToSave = DataUtils.getJohnDoeTransient();
        userRepository.save(userToSave);
        //when
        User userToUpdate = userRepository.findById(userToSave.getId())
                .orElse(null);
        userToUpdate.setEmail(updatedEmail);
        User updatedUser = userRepository.save(userToUpdate);
        //then
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getEmail()).isEqualTo(updatedEmail);
    }

    @Test
    @DisplayName("Test user is exists functionality")
    public void givenUserToSave_whenExistsById_thenUserIsFound() {
        //given
        User userToSave = DataUtils.getJohnDoeTransient();
        userRepository.save(userToSave);
        //when
        boolean existsById = userRepository.existsById(userToSave.getId());
        //then
        assertThat(existsById).isTrue();
    }

    @Test
    @DisplayName("Test user not found functionality")
    public void givenUserIsNotCreated_whenGetById_thenOptionalIsEmpty() {
        //given
        //when
        User obtainedUser = userRepository.findById(1L).orElse(null);
        //then
        assertThat(obtainedUser).isNull();
    }

    @Test
    @DisplayName("Test get all users functionality")
    public void givenThreeUsers_whenFindAll_thenAllUsersAreReturned() {
        //given
        User user1 = DataUtils.getJohnDoeTransient();
        User user2 = DataUtils.getMikeSmithTransient();
        User user3 = DataUtils.getFrankJonesTransient();

        userRepository.saveAll(List.of(user1, user2, user3));
        //when
        List<User> obtainedUsers = userRepository.findAll();
        //then
        assertThat(CollectionUtils.isEmpty(obtainedUsers)).isFalse();
        assertThat(obtainedUsers.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Test get all users by birth date between functionality")
    public void givenThreeUsers_whenFindAllByBirthDateBetween_thenTwoUsersAreReturned() {
        //given
        User user1 = DataUtils.getJohnDoeTransient();
        User user2 = DataUtils.getMikeSmithTransient();
        User user3 = DataUtils.getFrankJonesTransient();

        LocalDate user1BirthDate = user1.getBirthDate();
        LocalDate user2BirthDate = user2.getBirthDate();

        userRepository.saveAll(List.of(user1, user2, user3));
        //when
        List<User> obtainedUsers = userRepository.findAllByBirthDateBetween(user1BirthDate, user2BirthDate);
        //then
        assertThat(CollectionUtils.isEmpty(obtainedUsers)).isFalse();
        assertThat(obtainedUsers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("TEst delete user by id functionality")
    public void givenUserIsSaved_whenDeleteById_thenUserIsRemoved() {
        //given
        User userToSave = DataUtils.getJohnDoeTransient();
        userRepository.save(userToSave);
        //when
        userRepository.deleteById(userToSave.getId());
        User obtainedUser = userRepository.findById(userToSave.getId()).orElse(null);
        //then
        assertThat(obtainedUser).isNull();
    }
}
