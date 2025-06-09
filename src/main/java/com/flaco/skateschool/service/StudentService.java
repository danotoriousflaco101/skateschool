package com.flaco.skateschool.service;

import com.flaco.skateschool.dto.StudentDTO;
import com.flaco.skateschool.enums.SkateStyle;
import com.flaco.skateschool.enums.SkillLevel;
import com.flaco.skateschool.exception.ResourceNotFoundException;
import com.flaco.skateschool.mapper.StudentMapper;
import com.flaco.skateschool.model.Student;
import com.flaco.skateschool.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

   // Get all students
    public List<StudentDTO> findAll() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get student by ID
    public StudentDTO findById(Long id) {
        return studentRepository.findById(id)
                .map(studentMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    // Create a new student
    @Transactional
    public StudentDTO create(StudentDTO studentDTO) {
        Student student = studentMapper.toEntity(studentDTO);
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    // Update student by ID
    @Transactional
    public StudentDTO update(Long id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        studentMapper.updateStudentFromDto(studentDTO, student);
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    // Delete student by ID
    @Transactional
    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    // Get students by skate style
    public List<StudentDTO> findBySkateStyle(SkateStyle skateStyle) {
        return studentRepository.findBySkateStyle(skateStyle).stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get students by skill level
    public List<StudentDTO> findBySkillLevel(SkillLevel skillLevel) {
        return studentRepository.findBySkillLevel(skillLevel).stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }
}