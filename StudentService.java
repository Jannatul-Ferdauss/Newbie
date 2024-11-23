package com.example.LibraryMS.Service;

import com.example.LibraryMS.Dto.LoginDto;
import com.example.LibraryMS.Dto.SignupDto;
import com.example.LibraryMS.Entity.Student;

import java.util.Optional;

public interface StudentService {
    // Method to handle student signup
    String signup(SignupDto signupDto);

    // Method to handle student login
    Optional<Student> login(LoginDto loginDto);
}

