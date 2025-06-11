package com.flaco.skateschool.controller;

import com.flaco.skateschool.dto.UserDTO;
import com.flaco.skateschool.model.User;
import com.flaco.skateschool.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // Retrieve the currently authenticated user's information
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        UserDTO currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(currentUser);
    }

    // Retrieve a paginated list of all active users (admin only)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDTO>> getAllActiveUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.findAllActiveUsers(pageable));
    }

    // Retrieve a user by their ID (admin only)
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    // Create a new user (admin only)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDTO));
    }

    // Update an existing user's information (admin only)
    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(userId, userDTO));
    }

    // Update user's active status (activate or deactivate, admin only)
    @PatchMapping("/{userId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateUserStatus(
            @PathVariable Long userId,
            @RequestParam boolean active) {
        if (active) {
            userService.activateUser(userId);
        } else {
            userService.deactivateUser(userId);
        }
        return ResponseEntity.noContent().build();
    }

    // Delete a user (admin only)
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    // Test endpoint to verify access to protected resources
    @GetMapping("/test")
    public ResponseEntity<String> testProtectedEndpoint() {
        return ResponseEntity.ok("You have accessed a protected endpoint!");
    }

    // Endpoint accessible only to users with STUDENT role
    @GetMapping("/student-only")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> studentOnlyEndpoint() {
        return ResponseEntity.ok("This endpoint is accessible only to students!");
    }

    // Endpoint accessible only to users with TEACHER role
    @GetMapping("/teacher-only")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> teacherOnlyEndpoint() {
        return ResponseEntity.ok("This endpoint is accessible only to teachers!");
    }

    // Debugging endpoint to list all users (admin only)
    @GetMapping("/debug/users")
    public ResponseEntity<?> debugUsers() {
        List<UserDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }
}