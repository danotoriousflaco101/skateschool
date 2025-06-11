package com.flaco.skateschool.mapper;

import com.flaco.skateschool.dto.StudentDTO;
import com.flaco.skateschool.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentMapper {

    // Convert Student entity to StudentDTO
    @Mapping(target = "password", ignore = true)
    StudentDTO toDto(Student student);

    // Convert StudentDTO to Student entity
    @Mapping(target = "password", ignore = true)
    Student toEntity(StudentDTO studentDTO);

    // Update Student entity from StudentDTO
    @Mapping(target = "password", ignore = true)
    void updateStudentFromDto(StudentDTO studentDTO, @MappingTarget Student student);
}
