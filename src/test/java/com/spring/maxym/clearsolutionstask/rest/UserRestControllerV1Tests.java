package com.spring.maxym.clearsolutionstask.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.maxym.clearsolutionstask.dto.UserCreateDto;
import com.spring.maxym.clearsolutionstask.dto.UserResponseDto;
import com.spring.maxym.clearsolutionstask.dto.UserUpdateDto;
import com.spring.maxym.clearsolutionstask.entity.User;
import com.spring.maxym.clearsolutionstask.exception.IncorrectDateException;
import com.spring.maxym.clearsolutionstask.exception.UserCreationException;
import com.spring.maxym.clearsolutionstask.exception.UserNotFoundException;
import com.spring.maxym.clearsolutionstask.exception.UserUpdateException;
import com.spring.maxym.clearsolutionstask.mapper.UserMapper;
import com.spring.maxym.clearsolutionstask.service.BindingResultService;
import com.spring.maxym.clearsolutionstask.service.UserService;
import com.spring.maxym.clearsolutionstask.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.BindingResult;

import java.net.URI;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = UserRestControllerV1.class)
public class UserRestControllerV1Tests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private BindingResultService bindingResultService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test create user functionality")
    public void givenUserToCreate_whenCreateUser_thenSuccessResponse() throws Exception {
        //given
        UserCreateDto dto = DataUtils.getJohnDoeDtoToCreateTransient();
        User entity = DataUtils.getJohnDoePersisted();
        URI uri = URI.create("https://localhost:8080/api/v1/users/" + entity.getId());

        BDDMockito.given(userMapper.toEntityFromCreateDto(any(UserCreateDto.class)))
                .willReturn(entity);
        BDDMockito.given(userService.createUser(any(User.class)))
                .willReturn(uri);
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));
        //then
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.header().string("Location", uri.toString()));
    }

    @Test
    @DisplayName("Test create user with incorrect data functionality")
    public void givenIncorrectUserToCreate_whenCreateUser_thenFailureResponse() throws Exception {
        //given
        UserCreateDto dto = DataUtils.getJohnDoeDtoToCreateTransient();
        BDDMockito.doThrow(new UserCreationException(null)).when(bindingResultService).handle(any(BindingResult.class), any());
        //when
        ResultActions result = mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));
        //then
        verify(userMapper, never()).toEntityFromCreateDto(any(UserCreateDto.class));
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
        User entity = DataUtils.getJohnDoePersisted();

        BDDMockito.given(userService.getUserById(anyLong()))
                .willReturn(entity);
        BDDMockito.doNothing().when(userService).updateUser(any(User.class));
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
        UserCreateDto dto = DataUtils.getJohnDoeDtoToCreateTransient();
        BDDMockito.doThrow(new UserUpdateException(null)).when(bindingResultService).handle(any(BindingResult.class), any());
        //when
        ResultActions result = mockMvc.perform(patch("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));
        //then
        verify(userMapper, never()).updateUserFromDTO(any(UserUpdateDto.class), any(User.class));
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("User update error")));
    }

    @Test
    @DisplayName("Test get user by id functionality")
    public void givenId_whenGetUserById_thenSuccessResponse() throws Exception {
        //given
        User entity = DataUtils.getJohnDoePersisted();
        UserResponseDto responseDto = DataUtils.getJohnDoeResponseDto();
        BDDMockito.given(userService.getUserById(anyLong()))
                .willReturn(entity);
        BDDMockito.given(userMapper.toDto(entity))
                .willReturn(responseDto);
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
        User entity = DataUtils.getJohnDoePersisted();
        BDDMockito.given(userService.getUserById(anyLong()))
                .willThrow(new UserNotFoundException());

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/users/" + entity.getId()));
        //then
        verify(userMapper, never()).toDto(any(User.class));
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("User not found")));
    }

    @Test
    @DisplayName("Test get all users functionality")
    public void givenThreeUsers_whenGetUsers_thenSuccessResponse() throws Exception {
        //given
        User user1 = DataUtils.getJohnDoePersisted();
        User user2 = DataUtils.getMikeSmithPersisted();
        User user3 = DataUtils.getFrankJonesPersisted();
        List<User> users = List.of(user1, user2, user3);

        UserResponseDto userResponse1 = DataUtils.getJohnDoeResponseDto();
        UserResponseDto userResponse2 = DataUtils.getMikeSmithResponseDto();
        UserResponseDto userResponse3 = DataUtils.getFrankJonesResponseDto();
        List<UserResponseDto> usersResponse = List.of(userResponse1, userResponse2, userResponse3);

        BDDMockito.given(userService.getUsers(any(), any()))
                .willReturn(users);
        BDDMockito.given(userMapper.toListDto(users))
                .willReturn(usersResponse);
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].last_name", CoreMatchers.is(user2.getLastName())));
    }

    @Test
    @DisplayName("Test get all users exception functionality")
    public void givenThreeUsers_whenGetUsers_thenExceptionIsThrown() throws Exception {
        //given
        User user1 = DataUtils.getJohnDoePersisted();
        User user2 = DataUtils.getMikeSmithPersisted();
        User user3 = DataUtils.getFrankJonesPersisted();
        List<User> users = List.of(user1, user2, user3);

        BDDMockito.given(userService.getUsers(any(), any()))
                .willThrow(new IncorrectDateException("Start date cannot be after end date"));
        //when
        ResultActions result = mockMvc.perform(get("/api/v1/users"));
        //then
        verify(userMapper, never()).toListDto(users);
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("Start date cannot be after end date")));
    }

    @Test
    @DisplayName("Test delete user functionality")
    public void givenId_whenDeleteUser_thenSuccessResponse() throws Exception {
        //given
        BDDMockito.doNothing().when(userService).deleteUserById(anyLong());
        //when
        ResultActions result = mockMvc.perform(delete("/api/v1/users/1"));
        //then
        verify(userService, times(1)).deleteUserById(anyLong());
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Test delete user with incorrect id functionality")
    public void givenIncorrectId_whenDeleteUser_thenExceptionIsThrown() throws Exception {
        //given
        BDDMockito.doThrow(new UserNotFoundException()).when(userService).deleteUserById(anyLong());
        //when
        ResultActions result = mockMvc.perform(delete("/api/v1/users/1"));
        //then
        verify(userService, times(1)).deleteUserById(anyLong());
        verifyNoMoreInteractions(userMapper);
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is("User not found")));
    }
}
