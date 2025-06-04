package com.flaco.skateschool.mapper;

import com.flaco.skateschool.dto.TeacherDTO;
import com.flaco.skateschool.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TeacherMapper {
    TeacherDTO toDto(Teacher teacher);
    Teacher toEntity(TeacherDTO teacherDTO);
    void updateTeacherFromDto(TeacherDTO teacherDTO, @MappingTarget Teacher teacher);
}