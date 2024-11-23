package com.example.LibraryMS.Service;

import com.example.LibraryMS.Dto.LoginDto;
import com.example.LibraryMS.Dto.SignupDto;
import com.example.LibraryMS.Entity.Student;
import com.example.LibraryMS.Exception.StudentException;
import com.example.LibraryMS.Repository.StudentRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    // Constructor-based injection
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Implementation of signup method
    @Override
    public String signup(SignupDto signupDto) {
            // Check if student already exists
        try {
            // Check if student already exists
            if (studentRepository.existsByEmail(signupDto.getEmail())) {
                throw new StudentException("Student with this email already exists!");
            }
            // Convert DTO to Entity
        Student student = new Student();
        student.setName(signupDto.getName());
        student.setDept(signupDto.getDept());
        student.setAddress(signupDto.getAddress());
        student.setEmail(signupDto.getEmail());
        student.setBatch(signupDto.getBatch());
        student.setInterest(signupDto.getInterest());

        // Encrypt the password before saving
        String hashedPassword = BCrypt.hashpw(signupDto.getPassword(), BCrypt.gensalt());
        student.setPassword(hashedPassword);

        try {
            studentRepository.save(student);
        } catch (Exception ex) {
            throw new RuntimeException("Error saving student: " + ex.getMessage());
        }
        return "Student registered successfully!";
    } catch (StudentException ex) {
        throw new StudentException(ex.getMessage());
    } catch (Exception ex) {
        throw new RuntimeException("An error occurred during signup: " + ex.getMessage());
    }
    }

    // Implementation of login method
    @Override
    public Optional<Student> login(LoginDto loginDto) {
        // Find the student by email
        Optional<Student> studentOpt = studentRepository.findByEmail(loginDto.getEmail());

        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            // Check if the password matches
            if (BCrypt.checkpw(loginDto.getPassword(), student.getPassword())) {
                return Optional.of(student);
            } else {
                throw new StudentException("Invalid email or password!");
            }
        } else {
            throw new StudentException("Student not found with the given email!");
        }
    }
}
