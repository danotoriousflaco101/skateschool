package com.flaco.skateschool.repository;

import com.flaco.skateschool.model.Booking;
import com.flaco.skateschool.model.Lesson;
import com.flaco.skateschool.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStudentId(Long studentId);
    List<Booking> findByLessonId(Long lessonId);

    boolean existsByStudentAndLesson(Student student, Lesson lesson);
    Optional<Booking> findByStudentAndLesson(Student student, Lesson lesson);
}