package com.flaco.skateschool.controller;

import com.flaco.skateschool.dto.BookingDTO;
import com.flaco.skateschool.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    // Get all bookings
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')") // Only admin and/or teacher roles can access this endpoint
    public ResponseEntity<List<BookingDTO>> getAllBookings() { // Get all bookings
        return ResponseEntity.ok(bookingService.findAll());
    }

    // Get booking by its ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')") // Admin, teacher, and student roles can access this endpoint
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.findById(id));
    }

    // Create a new booking (only accessible to students)
    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<BookingDTO> createBooking(@Valid @RequestBody BookingDTO bookingDTO) {
        BookingDTO createdBooking = bookingService.createBooking(bookingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }

    // Update an existing booking (only accessible to admin and/or teacher)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long id, @Valid @RequestBody BookingDTO bookingDTO) {
        BookingDTO updatedBooking = bookingService.updateBooking(id, bookingDTO);
        return ResponseEntity.ok(updatedBooking);
    }

    // Delete a booking (only accessible to admin and/or teacher)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    // Confirm a booking (only accessible to admin and/or teacher)
    @PatchMapping("/{id}/confirm")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<BookingDTO> confirmBooking(@PathVariable Long id) {
        BookingDTO confirmedBooking = bookingService.confirmBooking(id);
        return ResponseEntity.ok(confirmedBooking);
    }

    // Retrieve all bookings for a specific student (Accessible to admin, teacher, and student)
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    public ResponseEntity<List<BookingDTO>> getBookingsByStudent(@PathVariable Long studentId) {
        List<BookingDTO> bookings = bookingService.findByStudentId(studentId);
        return ResponseEntity.ok(bookings);
    }

    // Retrieve all bookings for a specific lesson (accessible to admin and/or teacher)
    @GetMapping("/lesson/{lessonId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<List<BookingDTO>> getBookingsByLesson(@PathVariable Long lessonId) {
        List<BookingDTO> bookings = bookingService.findByLessonId(lessonId);
        return ResponseEntity.ok(bookings);
    }
}