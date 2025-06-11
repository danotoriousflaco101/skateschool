package com.flaco.skateschool.dto;

import com.flaco.skateschool.enums.SkateStyle;
import com.flaco.skateschool.enums.SkillLevel;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class LessonDTO {
    private Long id;

    @Size(max = 100) // Maximum length of the lesson title
    private String title;

    private String description; // Description of the lesson

    private LocalDateTime startTime; // Start time of the lesson

    private LocalDateTime endTime; // End time of the lesson

    @Min(1) @Max(20) // Maximum number of students allowed in the lesson
    private Integer maxStudents; // Maximum number of students allowed in the lesson

    private Long teacherId; // ID of the teacher who teaches the lesson

    private String teacherName; // Name of the teacher who teaches the lesson

    private SkateStyle skateStyle; // Type of lesson per skate style (STREET, VERT, FREESTYLE...)

    private SkillLevel skillLevel; // Skill level of the lesson

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}