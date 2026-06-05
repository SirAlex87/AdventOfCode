package com.borzacchiello.advent_of_code_controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * provare sul browser http://localhost:8080/api/ciao
 * http://localhost:8080/swagger-ui/index.html
 */
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api")
public class Task1Controller {
	
	Logger logger = LogManager.getLogger(Task1Controller.class);
	
	@Value("${welcome.message}")
	    private String welcomeMessage;
	// ------------------- Ricerca Per Codice ------------------------------------
	//api/ciao
		@RequestMapping(value = "/ciao", method = RequestMethod.GET, produces = "application/json")
		public ResponseEntity<String> helloWorld()  
				
		{
			logger.info("ciao2");


			return new ResponseEntity<String>(welcomeMessage, HttpStatus.OK);
		}



}