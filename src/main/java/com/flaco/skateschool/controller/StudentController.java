package com.flaco.skateschool.controller;

import com.flaco.skateschool.dto.StudentDTO;
import com.flaco.skateschool.enums.SkateStyle;
import com.flaco.skateschool.enums.SkillLevel;
import com.flaco.skateschool.service.StudentService;
import com.flaco.skateschool.exception.BadRequestException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    // Retrieve all students (accessible to all users)
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.findAll());
    }

    // Retrieve student by ID (accessible to all users)
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findById(id));
    }

    // Create a new student (all users)
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        if (studentDTO.getPassword() == null || studentDTO.getPassword().isEmpty()) {
            throw new BadRequestException("Password cannot be empty");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.create(studentDTO));
    }

    // Update an existing student's information (admin or teacher) or current user (self-update) (all users)'
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or @securityService.isCurrentUser(#id)")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.update(id, studentDTO));
    }

    // Delete a student (admin or teacher) or current user (self-delete) (all users)''
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or @securityService.isCurrentUser(#id)")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Retrieve students by skate style (accessible to all users)''
    @GetMapping("/by-style/{skateStyle}")
    public ResponseEntity<List<StudentDTO>> getStudentsBySkateStyle(@PathVariable SkateStyle skateStyle) {
        return ResponseEntity.ok(studentService.findBySkateStyle(skateStyle));
    }

    // Retrieve students by skill level (accessible to all users)''
    @GetMapping("/by-skill/{skillLevel}")
    public ResponseEntity<List<StudentDTO>> getStudentsBySkillLevel(@PathVariable SkillLevel skillLevel) {
        return ResponseEntity.ok(studentService.findBySkillLevel(skillLevel));
    }
}