package com.flaco.skateschool.dto;

import com.flaco.skateschool.enums.BookingStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDTO {
    private Long id;

    @NotNull
    private Long studentId;

    private String studentName;

    @NotNull
    private Long lessonId;

    private String lessonTitle;

    @NotNull
    private BookingStatus status;

    @Size(max = 500)
    private String notes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}