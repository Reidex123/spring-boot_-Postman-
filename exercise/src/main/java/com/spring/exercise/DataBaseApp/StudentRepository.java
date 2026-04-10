package com.spring.exercise.DataBaseApp;

/**
 * Enable Spring Data to generate SQL queries
 */
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.exercise.StudentStatus;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // SELECT * FROM students WHERE email = ?
    public Optional<Student> findByEmail(String email);

    // SELECT * FROM students WHERE year = ?
    public List<Student> findByYear(int year);

    // SELECT * FROM students WHERE name Like %keyword%
    public List<Student> findByNameContaining(String keyword);

    // SELECT * FROM students WHERE year = ? AND status = ?
    public List<Student> findByYearAndStatus(int year, StudentStatus status);

    // SELECT * FROM students WHERE year = ?
    public List<Student> findByGreaterThanEqual(int minYear);

    // SELECT * FROM students ORDER BY name ASC
    public List<Student> findAllByOrderByNameAsc();

    // SELECT COUNT(*) > 0 WHERE email = ?
    public boolean existsByEmail(String email);

    // SELECT * FROM students WHERE email = ? AND year = ?
    public Optional<Student> findByEmailAndYear(String email, int year);

    @Query("SELECT s FROM Student s WHERE s.year = :year AND s.status = 'ACTIVE'")
    public List<Student> findActiveStudentsByYear(@Param("year") int year);

    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c.code = :code")
    public List<Student> findStudentsEnrolledInCourse(@Param("code") String courseCode);

    // Native SQL (table/column)
    @Query(value = "SELECT * FROM students WHERE YEAR(enrollment_date) = :year", nativeQuery = true)
    public List<Student> findStudentsEnrolledInYear(@Param("year") int year);

    // update query
    @Modifying
    @Query("UPDATE Student s SET s.status = :status WHERE s.id = :id")
    public int updateStudentStatus(@Param("id") Long id, @Param("Status") StudentStatus status);
}
