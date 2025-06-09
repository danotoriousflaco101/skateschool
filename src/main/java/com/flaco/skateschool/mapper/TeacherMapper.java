package com.flaco.skateschool.mapper;

import com.flaco.skateschool.dto.TeacherDTO;
import com.flaco.skateschool.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

// Configure MapStruct mapper
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TeacherMapper {

    // Convert Teacher entity to TeacherDTO
    TeacherDTO toDto(Teacher teacher);

    // Convert TeacherDTO to Teacher entity
    Teacher toEntity(TeacherDTO teacherDTO);

    // Update Teacher entity from TeacherDTO
    void updateTeacherFromDto(TeacherDTO teacherDTO, @MappingTarget Teacher teacher);
}