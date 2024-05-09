package com.spring.maxym.clearsolutionstask.mapper;

import com.spring.maxym.clearsolutionstask.dto.UserCreateDto;
import com.spring.maxym.clearsolutionstask.dto.UserResponseDto;
import com.spring.maxym.clearsolutionstask.dto.UserUpdateDto;
import com.spring.maxym.clearsolutionstask.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toEntityFromCreateDto(UserCreateDto dto);

    @InheritInverseConfiguration
    UserResponseDto toDto(User user);

    @InheritConfiguration
    void updateUserFromDTO(UserUpdateDto dto, @MappingTarget User user);

    List<UserResponseDto> toListDto(List<User> users);
}
