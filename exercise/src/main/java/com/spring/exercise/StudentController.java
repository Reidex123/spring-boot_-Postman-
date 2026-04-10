package com.spring.exercise;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Student> getAll() {
        return service.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getOne(@PathVariable Long id) throws StudentNotFoundException {
        return service.getStudentById(id);
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student s) {
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
