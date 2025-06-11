package com.flaco.skateschool.repository;

import com.flaco.skateschool.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("SELECT l FROM Lesson l JOIN FETCH l.teacher")
    List<Lesson> findAllWithTeachers();

    @Query("SELECT l FROM Lesson l JOIN FETCH l.teacher WHERE l.teacher.id = :teacherId")
    List<Lesson> findByTeacherIdWithTeacher(Long teacherId);

    @Query("SELECT l FROM Lesson l JOIN FETCH l.teacher WHERE l.id = :id")
    Lesson findByIdWithTeacher(Long id);
}