package com.flaco.skateschool.mapper;

import com.flaco.skateschool.dto.StudentDTO;
import com.flaco.skateschool.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

// Configure MapStruct mapper
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentMapper {

    // Convert Student entity to StudentDTO
    StudentDTO toDto(Student student);

    // Convert StudentDTO to Student entity
    Student toEntity(StudentDTO studentDTO);

    // Update Student entity from StudentDTO
    void updateStudentFromDto(StudentDTO studentDTO, @MappingTarget Student student);
}