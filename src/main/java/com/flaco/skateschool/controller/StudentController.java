package com.flaco.skateschool.controller;

import com.flaco.skateschool.dto.StudentDTO;
import com.flaco.skateschool.enums.SkateStyle;
import com.flaco.skateschool.enums.SkillLevel;
import com.flaco.skateschool.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    // Get all students in the system (accessible to all users)
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.findAll());
    }

    // Get student by ID  (accessible to all users)
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findById(id));
    }

// Create a new student (accessible to all users)
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.create(studentDTO));
    }

    // Update an existing student's information  (accessible to all users)'
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.update(id, studentDTO));
    }

    // Delete a student (accessible to all users)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Retrieve students based on their preferred skate style
    @GetMapping("/by-style/{skateStyle}")
    public ResponseEntity<List<StudentDTO>> getStudentsBySkateStyle(@PathVariable SkateStyle skateStyle) {
        return ResponseEntity.ok(studentService.findBySkateStyle(skateStyle));
    }

    // Retrieve students based on their skill level
    @GetMapping("/by-skill/{skillLevel}")
    public ResponseEntity<List<StudentDTO>> getStudentsBySkillLevel(@PathVariable SkillLevel skillLevel) {
        return ResponseEntity.ok(studentService.findBySkillLevel(skillLevel));
    }
}