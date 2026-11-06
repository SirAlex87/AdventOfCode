package com.borzacchiello.advent_of_code.exception;

import java.time.Instant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.borzacchiello.advent_of_code.dto.response.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger= LogManager.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFile(
            InvalidFileException ex,
            HttpServletRequest request) {

        ErrorResponse response =
                new ErrorResponse(
                        Instant.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "INVALID_FILE",
                        ex.getMessage(),
                        request.getRequestURI()
                );

        return ResponseEntity
                .badRequest()
                .body(response);
    }
	
	@ExceptionHandler(MissingServletRequestPartException.class)
	public ResponseEntity<ErrorResponse> handleMissingServletRequestPart(
	        MissingServletRequestPartException exception,
	        HttpServletRequest request) {

	    ErrorResponse response = new ErrorResponse(
	            Instant.now(),
	            HttpStatus.BAD_REQUEST.value(),
	            HttpStatus.BAD_REQUEST.getReasonPhrase(),
	            "MISSING_FILE",
	            exception.getMessage(),
	            request.getRequestURI()
	    );

	    return ResponseEntity
	            .badRequest()
	            .body(response);
	}

	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleInvalidTaskInput(
			TaskNotFoundException ex,
	        HttpServletRequest request) {

	    ErrorResponse response =
	            new ErrorResponse(
	                    Instant.now(),
	                    HttpStatus.BAD_REQUEST.value(),
	                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
	                    "INVALID_TASK_INPUT",
	                    ex.getMessage(),
	                    request.getRequestURI()
	            );

	    return ResponseEntity
	            .badRequest()
	            .body(response);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(
	        Exception ex,
	        HttpServletRequest request) {

	    logger.error("Unexpected error", ex);

	    ErrorResponse response =
	            new ErrorResponse(
	                    Instant.now(),
	                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
	                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
	                    "INTERNAL_ERROR",
	                    "An unexpected error occurred",
	                    request.getRequestURI()
	            );

	    return ResponseEntity
	            .status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(response);
	}
}
