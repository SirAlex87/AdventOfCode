package com.borzacchiello.advent_of_code.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.borzacchiello.advent_of_code.service.UserService;
import com.borzacchiello.dto.UserDto;
import com.borzacchiello.advent_of_code.mapper.UserMapper;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService service;
  private final UserMapper mapper;

  public UserController(UserService service, UserMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @GetMapping
  public List<UserDto> all() {
    return service.findAll().stream().map(mapper::toDto).toList();
  }
}
