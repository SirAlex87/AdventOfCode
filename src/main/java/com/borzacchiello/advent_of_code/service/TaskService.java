package com.borzacchiello.advent_of_code.service;

import com.borzacchiello.advent_of_code.dto.request.TaskExecutionRequest;
import com.borzacchiello.advent_of_code.dto.response.TaskResult;
import com.borzacchiello.advent_of_code.exception.InvalidFileException;
import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.exception.TaskNotFoundException;


public interface TaskService {

	TaskResult execute(TaskExecutionRequest request) throws InvalidFileException, TaskNotFoundException, InvalidTaskInputException;

}
