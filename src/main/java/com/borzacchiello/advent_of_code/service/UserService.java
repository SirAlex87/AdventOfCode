package com.borzacchiello.advent_of_code.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.borzacchiello.advent_of_code.entity.User;
import com.borzacchiello.advent_of_code.repo.UserRepository;

@Service
public class UserService {
  private final UserRepository repo;

  public UserService(UserRepository repo) {
    this.repo = repo;
  }

  public List<User> findAll() {
    return repo.findAll();
  }
}
