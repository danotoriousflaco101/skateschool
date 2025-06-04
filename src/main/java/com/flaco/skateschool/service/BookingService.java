package com.flaco.skateschool.service;

import com.flaco.skateschool.dto.BookingDTO;
import com.flaco.skateschool.enums.BookingStatus;
import com.flaco.skateschool.exception.BadRequestException;
import com.flaco.skateschool.exception.ResourceNotFoundException;
import com.flaco.skateschool.mapper.BookingMapper;
import com.flaco.skateschool.model.*;
import com.flaco.skateschool.repository.BookingRepository;
import com.flaco.skateschool.repository.LessonRepository;
import com.flaco.skateschool.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;
    private final BookingMapper bookingMapper;

    public List<BookingDTO> findAll() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookingDTO findById(Long id) {
        return bookingRepository.findById(id)
                .map(bookingMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    @Transactional
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Lesson lesson = lessonRepository.findById(bookingDTO.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + bookingDTO.getLessonId()));

        Student student = studentRepository.findById(bookingDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + bookingDTO.getStudentId()));

        if (bookingRepository.existsByStudentAndLesson(student, lesson)) {
            throw new BadRequestException("Student has already booked this lesson");
        }

        if (lesson.isFull()) {
            throw new BadRequestException("Lesson is fully booked");
        }

        Booking booking = new Booking();
        booking.setLesson(lesson);
        booking.setStudent(student);
        booking.setStatus(BookingStatus.PENDING);
        booking.setNotes(bookingDTO.getNotes());

        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.toDto(savedBooking);
    }

    @Transactional
    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

        booking.setNotes(bookingDTO.getNotes());
        booking.setStatus(bookingDTO.getStatus());

        Booking updatedBooking = bookingRepository.save(booking);
        return bookingMapper.toDto(updatedBooking);
    }

    @Transactional
    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }

    @Transactional
    public BookingDTO confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new BadRequestException("Only pending bookings can be confirmed");
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        Booking confirmedBooking = bookingRepository.save(booking);
        return bookingMapper.toDto(confirmedBooking);
    }

    public List<BookingDTO> findByStudentId(Long studentId) {
        return bookingRepository.findByStudentId(studentId).stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookingDTO> findByLessonId(Long lessonId) {
        return bookingRepository.findByLessonId(lessonId).stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }
}