package com.example.LibraryMS.Controller;

import com.example.LibraryMS.Dto.LoginDto;
import com.example.LibraryMS.Dto.SignupDto;
import com.example.LibraryMS.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Endpoint for signing up a new student
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupDto signupDto) {
        studentService.signup(signupDto);
        return ResponseEntity.ok("Student registered successfully!");
    }

    // Endpoint for logging in an existing student
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDTO) {
        if (studentService.login(loginDTO).isPresent()) {
            return ResponseEntity.ok("Login successful!");
        }
        return ResponseEntity.status(401).body("Invalid email or password!");
    }
}

