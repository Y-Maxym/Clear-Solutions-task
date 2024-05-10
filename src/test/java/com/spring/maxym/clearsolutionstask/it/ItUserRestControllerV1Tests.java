package com.spring.maxym.clearsolutionstask.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.maxym.clearsolutionstask.dto.UserCreateDto;
import com.spring.maxym.clearsolutionstask.dto.UserUpdateDto;
import com.spring.maxym.clearsolutionstask.entity.User;
import com.spring.maxym.clearsolutionstask.repository.UserRepository;
import com.spring.maxym.clearsolutionstask.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ActiveProfiles("testcontainers")
@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItUserRestControllerV1Tests extends AbstractRestControllerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Test create user functionality")
    public void givenUserToCreate_whenCreateUser_thenSuccessResponse() throws Exception {
        //given
        UserCreateDto dto = DataUtils.getJohnDoeDtoToCreateTransient();
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));
        //then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"));
    }

    @Test
    @DisplayName("Test create user with incorrect data functionality")
    public void givenIncorrectUserToCreate_whenCreateUser_thenFailureResponse() throws Exception {
        //given
        UserCreateDto dto = DataUtils.getJohnDoeDtoToCreateIncorrect();
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));
        //then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.header().doesNotExist("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("User creation error")));
    }

    @Test
    @DisplayName("Test update user functionality")
    public void givenUserToUpdate_whenUpdateUser_thenSuccessResponse() throws Exception {
        //given
        UserUpdateDto dto = DataUtils.getJohnDoeDtoToUpdateTransient();
        User entity = DataUtils.getJohnDoeTransient();
        userRepository.save(entity);
        //when
        ResultActions result = mockMvc.perform(patch("/api/v1/users/" + entity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));
        //then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Test update user with incorrect data functionality")
    public void givenIncorrectUserToUpdate_whenUpdateUser_thenFailureResponse() throws Exception {
        //given
        UserUpdateDto dto = DataUtils.getJohnDoeDtoToUpdateIncorrect();
        //when
        ResultActions result = mockMvc.perform(patch("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));
        //then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("User update error")));
    }

    @Test
    @DisplayName("Test get user by id functionality")
    public void givenId_whenGetUserById_thenSuccessResponse() throws Exception {
        //given
        User entity = DataUtils.getFrankJonesTransient();
        userRepository.save(entity);
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/users/" + entity.getId()));
        //then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(entity.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.first_name", CoreMatchers.is(entity.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last_name", CoreMatchers.is(entity.getLastName())));
    }

    @Test
    @DisplayName("Test get user by incorrect id functionality")
    public void givenIncorrectId_whenGetUserById_thenFailureResponse() throws Exception {
        //given
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/users/1"));
        //then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("User not found")));
    }

    @Test
    @DisplayName("Test get all users functionality")
    public void givenThreeUsers_whenGetUsers_thenSuccessResponse() throws Exception {
        //given
        User user1 = DataUtils.getJohnDoeTransient();
        User user2 = DataUtils.getMikeSmithTransient();
        User user3 = DataUtils.getFrankJonesTransient();
        List<User> users = List.of(user1, user2, user3);

        userRepository.saveAll(users);
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/users"));
        //then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].email", CoreMatchers.is(user1.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].first_name", CoreMatchers.is(user1.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].last_name", CoreMatchers.is(user1.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].email", CoreMatchers.is(user2.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].first_name", CoreMatchers.is(user2.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].last_name", CoreMatchers.is(user2.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].email", CoreMatchers.is(user3.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].first_name", CoreMatchers.is(user3.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].last_name", CoreMatchers.is(user3.getLastName())));
    }

    @Test
    @DisplayName("Test get all users by birth date functionality")
    public void givenThreeUsers_whenGetUsersByBirthDate_thenOnlyTwoUsersIsReturned() throws Exception {
        //given
        User user1 = DataUtils.getJohnDoeTransient();
        User user2 = DataUtils.getMikeSmithTransient();
        User user3 = DataUtils.getFrankJonesTransient();
        List<User> users = List.of(user1, user2, user3);

        userRepository.saveAll(users);
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/users?from={from}&to={to}", user1.getBirthDate(), user2.getBirthDate()));
        //then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].email", CoreMatchers.is(user1.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].first_name", CoreMatchers.is(user1.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].last_name", CoreMatchers.is(user1.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].email", CoreMatchers.is(user2.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].first_name", CoreMatchers.is(user2.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].last_name", CoreMatchers.is(user2.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(2)));
    }

    @Test
    @DisplayName("Test get all users exception functionality")
    public void givenThreeUsers_whenGetUsers_thenExceptionIsThrown() throws Exception {
        //given
        User user1 = DataUtils.getJohnDoeTransient();
        User user2 = DataUtils.getMikeSmithTransient();
        User user3 = DataUtils.getFrankJonesTransient();
        List<User> users = List.of(user1, user2, user3);

        userRepository.saveAll(users);
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/users?from={from}&to={to}", user2.getBirthDate(), user1.getBirthDate()));
        //then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Start date cannot be after end date")));
    }

    @Test
    @DisplayName("Test delete user functionality")
    public void givenId_whenDeleteUser_thenSuccessResponse() throws Exception {
        //given
        User user = DataUtils.getJohnDoeTransient();
        userRepository.save(user);
        //when
        ResultActions result = mockMvc.perform(delete("/api/v1/users/" + user.getId()));
        //then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Test delete user with incorrect id functionality")
    public void givenIncorrectId_whenDeleteUser_thenExceptionIsThrown() throws Exception {
        //given
        //when
        ResultActions result = mockMvc.perform(delete("/api/v1/users/1"));
        //then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("User not found")));
    }

}
