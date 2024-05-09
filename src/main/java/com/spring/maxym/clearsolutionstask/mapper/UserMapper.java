package com.spring.maxym.clearsolutionstask.mapper;

import com.spring.maxym.clearsolutionstask.dto.UserCreateDto;
import com.spring.maxym.clearsolutionstask.dto.UserResponseDto;
import com.spring.maxym.clearsolutionstask.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toEntity(UserCreateDto dto);

    @InheritInverseConfiguration
    UserResponseDto toDto(User user);
}
