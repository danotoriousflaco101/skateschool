package com.flaco.skateschool.controller;

import com.flaco.skateschool.dto.LessonDTO;
import com.flaco.skateschool.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    // Get all lessons (accessible to all users)
    @GetMapping
    public ResponseEntity<List<LessonDTO>> getAllLessons() {
        return ResponseEntity.ok(lessonService.findAll());
    }

    // Get a specific lesson (accessible to all users)
    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.findById(id));
    }

    // Create a new lesson (only accessible to teachers)
    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<LessonDTO> createLesson(@Valid @RequestBody LessonDTO lessonDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lessonDTO));
    }

    // Update an existing lesson (only accessible to teachers)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<LessonDTO> updateLesson(@PathVariable Long id, @Valid @RequestBody LessonDTO lessonDTO) {
        return ResponseEntity.ok(lessonService.update(id, lessonDTO));
    }

    // Delete a lesson (only accessible to teachers)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Retrieve all lessons for a specific teacher
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<LessonDTO>> getLessonsByTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(lessonService.findByTeacherId(teacherId));
    }
}