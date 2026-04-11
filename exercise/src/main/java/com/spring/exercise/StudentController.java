package com.spring.exercise;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/api/students")
@Validated // enable method-level validation
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Student> getAll() {
        return service.getAllStudents();
    }

    @GetMapping
    public List<Student> getByYear(@RequestParam @Min(1) @Max(7) int year) {
        return service.getStudentsByYear(year);
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable @Positive(message = "ID must be positive") Long id) throws StudentNotFoundException {
        return service.getStudentById(id);
    }


    @PostMapping
    public ResponseEntity<StudentResponse> create(@RequestBody Student s) {
        return ResponseEntity.status(201).body(service.createStudent(s));
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @RequestBody Student s) throws StudentNotFoundException {
        return service.updateStudent(id, s);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws StudentNotFoundException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
