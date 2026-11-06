package com.borzacchiello.advent_of_code.exception;

public class InvalidTaskInputException
        extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidTaskInputException(String message) {
        super(message);
    }

    public InvalidTaskInputException(
            String message,
            Throwable cause) {

        super(message, cause);
    }
}