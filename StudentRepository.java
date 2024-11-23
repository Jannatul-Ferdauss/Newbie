package com.example.LibraryMS.Repository;

import com.example.LibraryMS.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Custom query method to find a student by email
    Optional<Student> findByEmail(String email);

    boolean existsByEmail(String email);
}

