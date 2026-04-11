package com.spring.exercise;

/**
 * Catch bad requests -> 400
 */
public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }
}
