package com.flaco.skateschool.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherDTO extends UserDTO { // Base class UserDTO
    @NotBlank @Size(max = 100)
    private String specialty;

    @NotNull @Min(0) @Max(50)
    private Integer yearsExperience;

    private Integer lessonsCount;
}