package com.flaco.skateschool.mapper;

import com.flaco.skateschool.dto.StudentDTO;
import com.flaco.skateschool.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentMapper {
    StudentDTO toDto(Student student);
    Student toEntity(StudentDTO studentDTO);
    void updateStudentFromDto(StudentDTO studentDTO, @MappingTarget Student student);
}