package com.flaco.skateschool.mapper;

import com.flaco.skateschool.dto.TeacherDTO;
import com.flaco.skateschool.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TeacherMapper {

    // Convert Teacher entity to TeacherDTO
    @Mapping(target = "password", ignore = true) // Ignora la password quando si converte da entità a DTO
    TeacherDTO toDto(Teacher teacher);

    // Convert TeacherDTO to Teacher entity
    @Mapping(target = "password", ignore = true) // Ignora la password quando si converte da DTO a entità
    @Mapping(target = "role", constant = "TEACHER") // Imposta il ruolo come TEACHER
    Teacher toEntity(TeacherDTO teacherDTO);

    // Update Teacher entity from TeacherDTO
    @Mapping(target = "password", ignore = true) // Ignora la password durante l'aggiornamento
    @Mapping(target = "role", ignore = true) // Ignora il ruolo durante l'aggiornamento
    void updateTeacherFromDto(TeacherDTO teacherDTO, @MappingTarget Teacher teacher);
}