package com.spring.exercise;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    @SuppressWarnings("null")
    @Transactional(readOnly = true)
    public Student getStudentById(Long id) throws StudentNotFoundException {
        return repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Student createStudent(Student student) {
        if (repository.existsByEmail(student.getEmail())) {
            throw new IllegalArgumentException("The Student with email " + student.getEmail() + " already exist!");
        }

        student.setEnrollmentDate(LocalDate.now());

        return repository.save(student);
    }

    public Student updateStudent(Long id, Student updatedData) throws StudentNotFoundException {
        Student exists = getStudentById(id);

        exists.setName(updatedData.getName());
        exists.setYear(updatedData.getYear());

        return repository.save(exists);
    }

    public void delete(Long id) throws StudentNotFoundException {
        Student student = getStudentById(id);

        if (!(student == null))
            repository.delete(student);

    }

    @Transactional(readOnly = true)
    public List<Student> getStudentsByYear(int year) {
        if (year < 1 || year > 7) {
            throw new IllegalArgumentException("Year must be between 1 & 7!");
        }

        return repository.findByYear(year);
    }


}
