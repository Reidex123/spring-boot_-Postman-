package com.spring.exercise;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

// DTO for updating (PUT/PATCH)
@Data
public class UpdateStudentRequest {
    @Size(min = 2, max = 100)
    private String name;
    @Min(1)
    @Max(7)
    private Integer year;

}
