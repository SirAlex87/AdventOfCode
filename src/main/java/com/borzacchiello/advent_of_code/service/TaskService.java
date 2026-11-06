package com.borzacchiello.advent_of_code.service;

import org.springframework.web.multipart.MultipartFile;

import com.borzacchiello.advent_of_code.dto.response.TaskResult;
import com.borzacchiello.advent_of_code.exception.InvalidFileException;
import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.exception.TaskNotFoundException;


public interface TaskService {

	TaskResult execute(int task, int part, MultipartFile file) throws InvalidFileException, TaskNotFoundException, InvalidTaskInputException;

}
