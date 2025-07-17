package com.example.demo.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.demo.Dto.RegisterRequest;
import com.example.demo.Dto.UserProfileDto;
import com.example.demo.Entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(RegisterRequest dto);

    UserProfileDto toUserProfileDto(User user);
}