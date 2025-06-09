package com.flaco.skateschool.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class LessonDTO {
    private Long id;

    @NotBlank @Size(max = 100)
    private String title;

    private String description; // Description of the lesson

    @NotNull @Future
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotNull @Min(1) @Max(20)
    private Integer maxStudents;

    @NotNull
    private Long teacherId;

    private String teacherName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}