package com.flaco.skateschool.service;

import com.flaco.skateschool.dto.StudentDTO;
import com.flaco.skateschool.enums.Role;
import com.flaco.skateschool.enums.SkateStyle;
import com.flaco.skateschool.enums.SkillLevel;
import com.flaco.skateschool.exception.ResourceNotFoundException;
import com.flaco.skateschool.mapper.StudentMapper;
import com.flaco.skateschool.model.Student;
import com.flaco.skateschool.repository.StudentRepository;
import com.flaco.skateschool.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService { // Service for managing student data
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;

    public List<StudentDTO> findAll() { // Retrieve all students
        return studentRepository.findAll().stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    public StudentDTO findById(Long id) { // Retrieve student by ID
        return studentRepository.findById(id)
                .map(studentMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    @Transactional
    public StudentDTO create(StudentDTO studentDTO) { // Create a new student
        Student student = studentMapper.toEntity(studentDTO);
        student.setPassword(passwordEncoder.encode(studentDTO.getPassword()));
        student.setRole(Role.STUDENT);
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    @Transactional
    public StudentDTO update(Long id, StudentDTO studentDTO) { // Update existing student's information (admin or teacher) or current user (self-update)'
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!userDetails.getUser().getId().equals(id) &&
                !userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_TEACHER"))) {
            throw new AccessDeniedException("You don't have permission to update this student");
        }

        studentMapper.updateStudentFromDto(studentDTO, student); // Update student's information'
        if (studentDTO.getPassword() != null && !studentDTO.getPassword().isEmpty()) {
            student.setPassword(passwordEncoder.encode(studentDTO.getPassword()));
        }
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    @Transactional
    public void delete(Long id) { // Delete a student (admin or teacher) or current user (self-delete)
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!userDetails.getUser().getId().equals(id) &&
                !userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_TEACHER"))) {
            throw new AccessDeniedException("You don't have permission to delete this student");
        }

        studentRepository.deleteById(id);
    }

    public List<StudentDTO> findBySkateStyle(SkateStyle skateStyle) { // Retrieve students by skate style
        return studentRepository.findBySkateStyle(skateStyle).stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<StudentDTO> findBySkillLevel(SkillLevel skillLevel) { // Retrieve students by skill level
        return studentRepository.findBySkillLevel(skillLevel).stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }
}