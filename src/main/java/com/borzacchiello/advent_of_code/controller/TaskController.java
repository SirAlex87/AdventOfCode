package com.borzacchiello.advent_of_code.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.borzacchiello.advent_of_code.dto.response.TaskResult;
import com.borzacchiello.advent_of_code.exception.InvalidFileException;
import com.borzacchiello.advent_of_code.exception.InvalidTaskInputException;
import com.borzacchiello.advent_of_code.exception.TaskNotFoundException;
import com.borzacchiello.advent_of_code.service.TaskService;

/**
 * provare sul browser http://localhost:8080/api/tasks/ciao
 * http://localhost:8080/swagger-ui/index.html
 * http://localhost:8080/h2-console
 */
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/tasks")
public class TaskController {
	
	private static final Logger logger = LogManager.getLogger(TaskController.class);
	
	@Value("${welcome.message}")
	    private String welcomeMessage;
	
	private TaskService taskService;
	
	public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
	
	
	// ------------------- Ricerca Per Codice ------------------------------------
	//api/tasks/ciao
		@GetMapping(value = "/ciao", produces = "application/json")
		public ResponseEntity<String> helloWorld()  
				
		{
			logger.info("ciao2");


			return new ResponseEntity<>(welcomeMessage, HttpStatus.OK);
		}
		
		 @PostMapping(
	        value = "/{task}/parts/{part}",
	        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	    )
	    public ResponseEntity<TaskResult> execute(
	            @PathVariable int task,
	            @PathVariable int part,
	            @RequestParam MultipartFile file) throws InvalidTaskInputException, InvalidFileException, TaskNotFoundException {

	        TaskResult result = taskService.execute(task, part, file);
			

	        return ResponseEntity.ok(result);
	    }



}