package com.flaco.skateschool.service;

import com.flaco.skateschool.dto.SignupRequest;
import com.flaco.skateschool.enums.Role;
import com.flaco.skateschool.enums.SkillLevel;
import com.flaco.skateschool.exception.BadRequestException;
import com.flaco.skateschool.exception.UserAlreadyExistsException;
import com.flaco.skateschool.model.*;
import com.flaco.skateschool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Create a new student account
    @Transactional
    public void registerUser(SignupRequest request) {
        logger.info("Attempting to register user: {}", request.getUsername());

        validateRequest(request);

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username '" + request.getUsername() + "' already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email '" + request.getEmail() + "' is already registered");
        }

        User user; // Create user based on role
        try {
            Role role = Role.valueOf(request.getRole().toUpperCase());

            if (role == Role.STUDENT) {
                user = createStudent(request);
            } else if (role == Role.TEACHER) {
                user = createTeacher(request);
            } else {
                throw new BadRequestException("Invalid role provided");
            }

            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setEmail(request.getEmail());
            user.setRole(role);

            userRepository.save(user);
            logger.info("User registered successfully: {}", request.getUsername());
        } catch (IllegalArgumentException e) {
            logger.error("Error during user registration", e);
            throw new BadRequestException("Invalid role or skill level provided");
        }
    }
    // Validate signup request fields and throw appropriate exceptions if invalid
    private void validateRequest(SignupRequest request) {
        if (request.getUsername() == null || request.getEmail() == null ||
                request.getPassword() == null || request.getRole() == null) {
            throw new BadRequestException("All fields are required");
        }
    }

    // Create a new student account (with skate style)
    private Student createStudent(SignupRequest request) {
        Student student = new Student();
        if (request.getSkillLevel() != null) {
            try {
                student.setSkillLevel(SkillLevel.valueOf(request.getSkillLevel().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Invalid skill level provided");
            }
        }
        student.setSkateStyle(request.getSkateStyle());
        return student;
    }

    // Create a new teacher account (with years of experience)
    private Teacher createTeacher(SignupRequest request) {
        Teacher teacher = new Teacher();
        if (request.getYearsExperience() == null || request.getYearsExperience() < 0) {
            throw new BadRequestException("Years of experience must be a positive number");
        }
        teacher.setSpecialty(request.getSpecialty());
        teacher.setYearsExperience(request.getYearsExperience());
        return teacher;
    }
}