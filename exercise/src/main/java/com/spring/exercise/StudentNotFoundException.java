package com.spring.exercise;

/**
 * Find non-existent request resource -> 404
 */
public class StudentNotFoundException extends RuntimeException {

    private final Long studentId;

    public StudentNotFoundException(Long id) {
        super("Student with ID " + id + " was not found!");
        this.studentId = id;
    }

    public Long getStudentId() {
        return this.studentId;
    }
}
