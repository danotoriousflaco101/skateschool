package com.flaco.skateschool.service;

import com.flaco.skateschool.dto.LessonDTO;
import com.flaco.skateschool.dto.TeacherDTO;
import com.flaco.skateschool.enums.Role;
import com.flaco.skateschool.exception.ResourceNotFoundException;
import com.flaco.skateschool.mapper.LessonMapper;
import com.flaco.skateschool.mapper.TeacherMapper;
import com.flaco.skateschool.model.Teacher;
import com.flaco.skateschool.repository.TeacherRepository;
import com.flaco.skateschool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final TeacherMapper teacherMapper;
    private final LessonMapper lessonMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<TeacherDTO> findAll() { // Get all teachers with their lessons
        return teacherRepository.findAll()
                .stream()
                .map(teacherMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public TeacherDTO getTeacherById(Long id) { // Get teacher with their lessons by ID
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        return teacherMapper.toDto(teacher);
    }

    public TeacherDTO createTeacher(TeacherDTO teacherDTO) { // Create a new teacher
        if (userRepository.existsByUsername(teacherDTO.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }
        if (userRepository.existsByEmail(teacherDTO.getEmail())) {
            throw new IllegalArgumentException("Email is already taken");
        }

        Teacher teacher = teacherMapper.toEntity(teacherDTO);

        if (teacherDTO.getPassword() == null || teacherDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        teacher.setPassword(passwordEncoder.encode(teacherDTO.getPassword()));
        teacher.setRole(Role.TEACHER);

        try {
            teacher = teacherRepository.save(teacher);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Unable to create teacher. The username or email might be already in use.");
        }
        return teacherMapper.toDto(teacher);
    }

    public TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO) { // Update an existing teacher
        Teacher existingTeacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));

        if (teacherDTO.getUsername() != null && !teacherDTO.getUsername().trim().isEmpty()) {
            if (!existingTeacher.getUsername().equals(teacherDTO.getUsername()) &&
                    userRepository.existsByUsername(teacherDTO.getUsername())) {
                throw new IllegalArgumentException("Username is already taken");
            }
            existingTeacher.setUsername(teacherDTO.getUsername());
        }
        if (teacherDTO.getEmail() != null && !teacherDTO.getEmail().trim().isEmpty()) {
            if (!existingTeacher.getEmail().equals(teacherDTO.getEmail()) &&
                    userRepository.existsByEmail(teacherDTO.getEmail())) {
                throw new IllegalArgumentException("Email is already taken");
            }
            existingTeacher.setEmail(teacherDTO.getEmail());
        }
        if (teacherDTO.getSpecialty() != null && !teacherDTO.getSpecialty().trim().isEmpty()) {
            existingTeacher.setSpecialty(teacherDTO.getSpecialty());
        }
        if (teacherDTO.getYearsExperience() != null) {
            existingTeacher.setYearsExperience(teacherDTO.getYearsExperience());
        }

        existingTeacher.setRole(Role.TEACHER);

        if (teacherDTO.getPassword() != null && !teacherDTO.getPassword().isEmpty()) {
            existingTeacher.setPassword(passwordEncoder.encode(teacherDTO.getPassword()));
        }

        try {
            existingTeacher = teacherRepository.save(existingTeacher);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Unable to update teacher. The username or email might be already in use.");
        }
        return teacherMapper.toDto(existingTeacher);
    }

    public void deleteTeacher(Long id) { // Delete a teacher
        if (!teacherRepository.existsById(id)) {
            throw new ResourceNotFoundException("Teacher not found");
        }
        teacherRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<LessonDTO> getLessonsByTeacherId(Long teacherId) { // Get lessons by teacher ID
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        Hibernate.initialize(teacher.getLessons());
        return teacher.getLessons().stream()
                .map(lessonMapper::toDto)
                .collect(Collectors.toList());
    }

    public void updateTeacherActiveStatus(Long id, boolean active) { // Update teacher's active status'
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        teacher.setActive(active);
        teacherRepository.save(teacher);
    }

    @Transactional(readOnly = true)
    public List<TeacherDTO> getTeachersBySpecialty(String specialty) { // Get teachers by specialty
        return teacherRepository.findBySpecialty(specialty).stream()
                .map(teacherMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TeacherDTO> getAllTeachersSortedByExperience() { // Get all teachers sorted by years of experience
        return teacherRepository.findAll(Sort.by(Sort.Direction.DESC, "yearsExperience")).stream()
                .map(teacherMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Map<Long, Long> getTeacherLessonCounts() { // Get teacher ID and number of lessons for all teachers
        List<Teacher> teachers = teacherRepository.findAll();
        teachers.forEach(teacher -> Hibernate.initialize(teacher.getLessons()));
        return teachers.stream()
                .collect(Collectors.toMap(
                        Teacher::getId,
                        teacher -> (long) teacher.getLessons().size()
                ));
    }
}