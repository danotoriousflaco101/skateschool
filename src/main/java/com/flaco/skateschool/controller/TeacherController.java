package com.flaco.skateschool.controller;

import com.flaco.skateschool.dto.LessonDTO;
import com.flaco.skateschool.dto.TeacherDTO;
import com.flaco.skateschool.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    // Retrieve all teachers (accessible to all users)
    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.findAll());
    }

    // Retrieve teacher by ID (accessible to all users)
    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    // create a new teacher (accessible to Admin)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeacherDTO> createTeacher(@Valid @RequestBody TeacherDTO teacherDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.createTeacher(teacherDTO));
    }

    // Update an existing teacher (accessible to Admin)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable Long id, @Valid @RequestBody TeacherDTO teacherDTO) {
        return ResponseEntity.ok(teacherService.updateTeacher(id, teacherDTO));
    }

    // Delete a teacher (accessible to Admin)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    // Retrieve lessons taught by a teacher (accessible to all users)
    @GetMapping("/{id}/lessons")
    public ResponseEntity<List<LessonDTO>> getTeacherLessons(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getLessonsByTeacherId(id));
    }

    // Update teacher status (accessible to Admin)
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateTeacherStatus(@PathVariable Long id, @RequestParam boolean active) {
        teacherService.updateTeacherActiveStatus(id, active);
        return ResponseEntity.noContent().build();
    }

    // Retrieve teachers by specialty (accessible to all users)
    @GetMapping("/by-specialty")
    public ResponseEntity<List<TeacherDTO>> getTeachersBySpecialty(@RequestParam(required = false) String specialty) {
        if (specialty == null || specialty.isEmpty()) {
            return ResponseEntity.ok(teacherService.findAll());
        }
        return ResponseEntity.ok(teacherService.getTeachersBySpecialty(specialty));
    }

    // Retrieve teachers sorted by experience (accessible to all users)
    @GetMapping("/sorted-by-experience")
    public ResponseEntity<List<TeacherDTO>> getTeachersSortedByExperience() {
        return ResponseEntity.ok(teacherService.getAllTeachersSortedByExperience());
    }

    // Retrieve teachers with the most lessons (accessible to all users)
    @GetMapping("/lesson-counts")
    public ResponseEntity<Map<Long, Long>> getTeacherLessonCounts() {
        return ResponseEntity.ok(teacherService.getTeacherLessonCounts());
    }
}