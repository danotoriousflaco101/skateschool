package com.flaco.skateschool.dto;

import com.flaco.skateschool.enums.BookingStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDTO { // Define the BookingDTO class with necessary fields
    private Long id;

    @NotNull
    private Long studentId; // The ID of the student making the booking

    private String studentName; // The name of the student making the booking

    @NotNull
    private Long lessonId; // The ID of the lesson being booked

    private String lessonTitle; // The title of the lesson being booked

    @NotNull
    private BookingStatus status = BookingStatus.PENDING; // Booking status, default value is PENDING

    @Size(max = 500)
    private String notes; // Additional notes for the booking

    private LocalDateTime createdAt; // Timestamp for when the booking was created
    private LocalDateTime updatedAt; // Timestamp for when the booking was last updated
}