package com.flaco.skateschool.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherDTO extends UserDTO {
    @NotBlank(message = "Specialty cannot be blank")
    @Size(max = 100, message = "Specialty must be at most 100 characters")
    private String specialty;

    @NotNull(message = "Years of experience cannot be null")
    @Min(value = 0, message = "Years of experience must be at least 0")
    @Max(value = 50, message = "Years of experience must be at most 50")
    private Integer yearsExperience;

    private Integer lessonsCount;

    private String password;

    // getRole() and setRole() for TEACHER to reduce redundancy in the API responses.
    @JsonIgnore
    @Override
    public String getRole() {
        return "TEACHER";
    }

    @JsonIgnore
    @Override
    public void setRole(String role) {
        // No-op: Teacher role is fixed and cannot be changed
    }
}