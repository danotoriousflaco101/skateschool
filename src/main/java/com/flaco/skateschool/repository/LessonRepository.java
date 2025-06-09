package com.flaco.skateschool.repository;

import com.flaco.skateschool.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByTeacherId(Long teacherId); // Retrieve all lessons for a specific teacher
}