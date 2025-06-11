package com.flaco.skateschool.service;

import com.flaco.skateschool.dto.LessonDTO;
import com.flaco.skateschool.exception.BadRequestException;
import com.flaco.skateschool.exception.ResourceNotFoundException;
import com.flaco.skateschool.mapper.LessonMapper;
import com.flaco.skateschool.model.*;
import com.flaco.skateschool.repository.LessonRepository;
import com.flaco.skateschool.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
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

    @Transactional(readOnly = true)
    public List<LessonDTO> findAll() { // Get all lessons with their teachers
        return lessonRepository.findAllWithTeachers()
                .stream()
                .map(lessonMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public LessonDTO findById(Long id) { // Get lesson with its teacher by ID
        Lesson lesson = lessonRepository.findByIdWithTeacher(id);
        if (lesson == null) {
            throw new ResourceNotFoundException("Lesson not found with id: " + id);
        }
        return lessonMapper.toDto(lesson);
    }

    @Transactional
    public LessonDTO save(LessonDTO lessonDTO) { // Create new lesson with teacher
        Teacher teacher = getTeacher(lessonDTO.getTeacherId());
        Lesson lesson = lessonMapper.toEntity(lessonDTO);
        lesson.setTeacher(teacher);
        validateLesson(lesson);
        Lesson savedLesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(savedLesson);
    }

    @Transactional
    public LessonDTO update(Long id, LessonDTO lessonDTO) { // Update existing lesson with teacher
        Lesson existingLesson = lessonRepository.findByIdWithTeacher(id);
        if (existingLesson == null) {
            throw new ResourceNotFoundException("Lesson not found with id: " + id);
        }

        if (lessonDTO.getTitle() != null) {
            existingLesson.setTitle(lessonDTO.getTitle());
        }
        if (lessonDTO.getDescription() != null) {
            existingLesson.setDescription(lessonDTO.getDescription());
        }
        if (lessonDTO.getStartTime() != null) {
            if (lessonDTO.getStartTime().isBefore(LocalDateTime.now())) {
                throw new BadRequestException("Lesson start time must be in the future");
            }
            existingLesson.setStartTime(lessonDTO.getStartTime());
        }
        if (lessonDTO.getEndTime() != null) {
            existingLesson.setEndTime(lessonDTO.getEndTime());
        }
        if (lessonDTO.getMaxStudents() != null) {
            existingLesson.setMaxStudents(lessonDTO.getMaxStudents());
        }
        if (lessonDTO.getTeacherId() != null && !lessonDTO.getTeacherId().equals(existingLesson.getTeacher().getId())) {
            Teacher teacher = getTeacher(lessonDTO.getTeacherId());
            existingLesson.setTeacher(teacher);
        }
        if (lessonDTO.getSkateStyle() != null) {
            existingLesson.setSkateStyle(lessonDTO.getSkateStyle());
        }
        if (lessonDTO.getSkillLevel() != null) {
            existingLesson.setSkillLevel(lessonDTO.getSkillLevel());
        }

        validateLesson(existingLesson);

        Lesson updatedLesson = lessonRepository.save(existingLesson);
        return lessonMapper.toDto(updatedLesson);
    }

    // Delete a lesson
    @Transactional
    public void delete(Long id, Long currentUserId, boolean isAdmin) {
        Lesson lesson = lessonRepository.findByIdWithTeacher(id);
        if (lesson == null) {
            throw new ResourceNotFoundException("Lesson not found with id: " + id);
        }

        if (!isAdmin && !lesson.getTeacher().getId().equals(currentUserId)) {
            throw new AccessDeniedException("You don't have permission to delete this lesson");
        }

        lessonRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<LessonDTO> findByTeacherId(Long teacherId) { // Get all lessons by teacher ID with their teachers
        return lessonRepository.findByTeacherIdWithTeacher(teacherId)
                .stream()
                .map(lessonMapper::toDto)
                .toList();
    }

    private void validateLesson(Lesson lesson) { // Validate lesson data
        if (lesson.getStartTime() != null && lesson.getEndTime() != null) {
            if (lesson.getEndTime().isBefore(lesson.getStartTime())) {
                throw new BadRequestException("End time must be after start time");
            }
        }
        if (lesson.getMaxStudents() != null && lesson.getMaxStudents() <= 0) {
            throw new BadRequestException("Max students must be a positive number");
        }
    }

    private Teacher getTeacher(Long teacherId) { // Get teacher by ID
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + teacherId));
    }
}