package com.flaco.skateschool.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO { // Base class for user DTOs
    private Long id;

    @NotBlank @Size(min = 3, max = 50)
    private String username;

    @NotBlank @Email
    private String email;

    @NotBlank
    private String role; // "STUDENT" or "TEACHER"

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}