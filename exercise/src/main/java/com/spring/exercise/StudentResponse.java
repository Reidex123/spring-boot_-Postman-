package com.spring.exercise;

import java.time.LocalDate;

import lombok.Data;

// DTO for response (what the client receives)
@Data
public class StudentResponse {
    private Long id;
    private String name;
    private String email;
    private int year;
    private LocalDate enrollmentDate;
    private String status;
}
