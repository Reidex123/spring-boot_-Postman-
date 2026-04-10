package com.spring.exercise;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // generates no-arg constructor required for Jackson
@AllArgsConstructor

public class Student {
    private Long id;
    private String name;
    private String email;
    private int year;

}
