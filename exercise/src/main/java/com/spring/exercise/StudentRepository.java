package com.spring.exercise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    public boolean existsByEmail(String email);

    public List<Student> findByYear(int year);

}
