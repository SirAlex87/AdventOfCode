package com.borzacchiello.advent_of_code.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.borzacchiello.advent_of_code.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {}
