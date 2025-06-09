package com.flaco.skateschool.model;

import com.flaco.skateschool.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student; // Student who made the booking

    @ManyToOne
    private Lesson lesson; // The lesson being booked

    private String notes; // Additional notes for the booking

    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.PENDING; // Booking status, Default value is PENDING

}