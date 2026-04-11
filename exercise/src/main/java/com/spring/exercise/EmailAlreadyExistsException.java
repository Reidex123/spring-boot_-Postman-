package com.spring.exercise;

/**
 * Catch duplicate email's -> 409
 */
public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("Student with email " + email + " already exist.");
    }
}
