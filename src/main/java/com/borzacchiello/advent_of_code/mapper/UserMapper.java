package com.borzacchiello.advent_of_code.mapper;

import org.mapstruct.Mapper;

import com.borzacchiello.dto.UserDto;
import com.borzacchiello.advent_of_code.domain.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDto toDto(User u);
  User toEntity(UserDto dto);
}
