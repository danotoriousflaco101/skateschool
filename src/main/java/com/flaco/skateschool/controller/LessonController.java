package com.flaco.skateschool.controller;

import com.flaco.skateschool.dto.LessonDTO;
import com.flaco.skateschool.security.UserDetailsImpl;
import com.flaco.skateschool.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    // Get lesson by ID (accessible to all users)
    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.findById(id));
    }

    // Create a new lesson (accessible to Admin and/or teachers)
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<LessonDTO> createLesson(@Valid @RequestBody LessonDTO lessonDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lessonDTO));
    }

    // Update an existing lesson (accessible to admin and/or teacher)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<LessonDTO> updateLesson(@PathVariable Long id, @Valid @RequestBody LessonDTO lessonDTO) {
        LessonDTO updatedLesson = lessonService.update(id, lessonDTO);
        return ResponseEntity.ok(updatedLesson);
    }

    // Delete a lesson (accessible to admin and/or teacher)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        boolean isAdmin = userDetails.getAuthorities().stream() // Check if user has admin authority
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")); // Check if user is admin or teacher
        lessonService.delete(id, userDetails.getUser().getId(), isAdmin);
        return ResponseEntity.noContent().build();
    }

    // Get lessons by teacher ID (accessible to all users)
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<LessonDTO>> getLessonsByTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(lessonService.findByTeacherId(teacherId));
    }
}