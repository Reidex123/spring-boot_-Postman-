package com.spring.exercise;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        repository.save(new Student(null, "koketso", "k@uct.ac.za", 2));
        repository.save(new Student(null, "other", "o@uct.ac.za", 3));
    }

    @Test
    void findByEmail_returnsStudent() {
        Optional<Student> result = repository.findByEmail("k@uct.ac.za");

        assertThat(result).isPresent();
        assertThat((result).get().getName()).isEqualTo("koketso");
    }

    @Test
    void existsByEmail_returnsTrueWhenExists() {
        assertThat(repository.existsByEmail("k@uct.ac.za")).isTrue();
        assertThat(repository.existsByEmail("nobody@uct.ac.za")).isFalse();
    }

    @Test
    void findByYear_returnsOnlyMatchingYear() {
        List<Student> year2 = repository.findByYear(2);

        assertThat(year2).hasSize(1);
        assertThat(year2.get(0).getEmail()).isEqualTo("k@uct.ac.za");
    }

}
