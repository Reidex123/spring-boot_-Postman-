package com.spring.exercise;

/**
 * Student Controller class to manage students in-memory
 * @PathVariable -> extracting a certain value in Jackson file
 * @RequestBody -> convert the body of JSON to a java Object
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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

    private final List<Student> students = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @GetMapping
    public List<Student> getAllStudents() {
        return students;
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return students.stream().filter(s -> s.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RuntimeException("Student not found!"));
    }

    @PostMapping // create a new student
    public Student createStudent(@RequestBody Student student) {
        student.setId(idCounter.getAndIncrement());
        students.add(student);

        return student;
    }

    @PutMapping("/{id}") // update details of student with the specified id
    public Student updateStudent(@PathVariable Long id, @RequestBody Student updated) {
        Student existing = getStudent(id);

        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setYear(updated.getYear());

        return existing;
    }

    @DeleteMapping("/{id}") // delete the student with specified id from the database
    public String deleteStudent(@PathVariable Long id) {
        Student student = getStudent(id);

        students.remove(student);

        return "Student " + id + " deleted.";
    }

}
