package com.flaco.skateschool.service;

import com.flaco.skateschool.dto.LessonDTO;
import com.flaco.skateschool.exception.BadRequestException;
import com.flaco.skateschool.exception.ResourceNotFoundException;
import com.flaco.skateschool.mapper.LessonMapper;
import com.flaco.skateschool.model.*;
import com.flaco.skateschool.repository.LessonRepository;
import com.flaco.skateschool.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final TeacherRepository teacherRepository;
    private final LessonMapper lessonMapper;

    // Get all lessons
    public List<LessonDTO> findAll() {
        return lessonRepository.findAll()
                .stream()
                .map(lessonMapper::toDto)
                .toList();
    }

    // Get a lesson by id
    public LessonDTO findById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + id));
        return lessonMapper.toDto(lesson);
    }

    // Save a new lesson
    @Transactional
    public LessonDTO save(LessonDTO lessonDTO) { // Save a new lesson
        validateLesson(lessonDTO);
        Teacher teacher = getTeacher(lessonDTO.getTeacherId());
        Lesson lesson = lessonMapper.toEntity(lessonDTO);
        lesson.setTeacher(teacher);
        Lesson savedLesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(savedLesson);
    }

    // Update an existing lesson
    @Transactional
    public LessonDTO update(Long id, LessonDTO lessonDTO) {
        Lesson existingLesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found with id: " + id));
        validateLesson(lessonDTO);
        Teacher teacher = getTeacher(lessonDTO.getTeacherId());
        lessonMapper.updateLessonFromDto(lessonDTO, existingLesson);
        existingLesson.setTeacher(teacher);
        Lesson updatedLesson = lessonRepository.save(existingLesson);
        return lessonMapper.toDto(updatedLesson);
    }

    // Delete a lesson by id
    @Transactional
    public void delete(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lesson not found with id: " + id);
        }
        lessonRepository.deleteById(id);
    }

    // Get lessons by teacher id
    public List<LessonDTO> findByTeacherId(Long teacherId) {
        return lessonRepository.findByTeacherId(teacherId)
                .stream()
                .map(lessonMapper::toDto)
                .toList();
    }

    // Validate lesson details
    private void validateLesson(LessonDTO lessonDTO) {
        if (lessonDTO.getStartTime().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Lesson start time must be in the future");
        }
        if (lessonDTO.getEndTime().isBefore(lessonDTO.getStartTime())) {
            throw new BadRequestException("End time must be after start time");
        }
        if (lessonDTO.getMaxStudents() == null || lessonDTO.getMaxStudents() <= 0) {
            throw new BadRequestException("Max students must be a positive number");
        }
    }

    // Get teacher by id
    private Teacher getTeacher(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + teacherId));
    }
}