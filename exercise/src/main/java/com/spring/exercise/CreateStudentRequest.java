package com.spring.exercise;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

// DTO for creating POST
@Data
public class CreateStudentRequest {

    @UCTEmail
    private String name;
    @Email
    private String email;
    @Min(1)
    @Max(7)
    private Integer year;

}
