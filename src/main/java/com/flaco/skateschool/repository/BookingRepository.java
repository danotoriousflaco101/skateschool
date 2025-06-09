package com.flaco.skateschool.repository;

import com.flaco.skateschool.model.Booking;
import com.flaco.skateschool.model.Lesson;
import com.flaco.skateschool.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByStudentId(Long studentId); // Find all bookings for a specific student
    List<Booking> findByLessonId(Long lessonId); // Find all bookings for a specific lesson

    // Checks if a booking exists for a given student and lesson
    boolean existsByStudentAndLesson(Student student, Lesson lesson);
}