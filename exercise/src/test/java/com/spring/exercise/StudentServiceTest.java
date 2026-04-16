package com.spring.exercise;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentService service; // Create StudentService and inject the mock

    @Test
    @DisplayName("getAllStudents returns a list of students")
    void getAllStudents_returnsAll() {

        List<Student> expected = List.of(new Student(1L, "Koketso", "k@uct.ac.za", 2), new Student(2L, "Example", "e@uct.ac.za", 3));
        given(repository.findAll()).willReturn(expected);

        List<Student> result = service.getAllStudents();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Koketso");
        verify(repository).findAll();
    }

    @Test
    @DisplayName("getStudentById throws when student not found")
    void getStudentById_notFound_throwsException() {

        given(repository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.getStudentById(99L)).isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("createStudent saves and return the student")
    void createStudent_success() {

        Student input = new Student(null, "Koketso", "k@uct.ac.za", 2);
        Student saved = new Student(1L, "Koketso", "k@uct.ac.za", 2);

        given(repository.existsByEmail("k@uct.ac.za")).willReturn(false);
        given(repository.save(any(Student.class))).willReturn(saved);

        StudentResponse result = service.createStudent(input);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Koketso");
        then(repository).should().save(any(Student.class));

    }

    @Test
    @DisplayName("createStudent throws when email already exists")
    void createStudent_duplicateEmail_throws() {

        Student input = new Student(null, "Koketso", "existing@uct.ac.za", 1);

        given(repository.existsByEmail("existing@uct.ac.za")).willReturn(true);

        assertThatThrownBy(() -> service.createStudent(input)).isInstanceOf(IllegalArgumentException.class);

        then(repository).should(never()).save(any());
    }

}
