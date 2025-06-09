package com.flaco.skateschool.dto;

import com.flaco.skateschool.enums.SkateStyle;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest { // Class for signup request DTO
    @NotBlank @Size(min = 3, max = 50)
    private String username;

    @NotBlank @Size(min = 8)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = "Must contain at least 1 uppercase, 1 lowercase and 1 digit..You know how it works!")
    private String password;

    @NotBlank @Email
    private String email;

    @NotBlank
    private String role;

    // Student fields
    private String skillLevel;
    private SkateStyle skateStyle;

    // Teacher fields
    @Size(max = 100)
    private String specialty;

    @Min(0) @Max(50)
    private Integer yearsExperience;
}