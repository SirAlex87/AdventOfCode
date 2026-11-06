package com.borzacchiello.advent_of_code.mapper;

import org.mapstruct.Mapper;

import com.borzacchiello.advent_of_code.dto.response.UserDto;
import com.borzacchiello.advent_of_code.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDto toDto(User u);
  User toEntity(UserDto dto);
}
