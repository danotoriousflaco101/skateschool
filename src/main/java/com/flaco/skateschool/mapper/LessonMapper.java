package com.flaco.skateschool.mapper;

import com.flaco.skateschool.dto.LessonDTO;
import com.flaco.skateschool.model.Lesson;
import org.mapstruct.*;

// Configure MapStruct mapper
@Mapper(componentModel = "spring",
        uses = {TeacherMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LessonMapper {

    // Convert Lesson entity to LessonDTO
    @Mapping(target = "teacherId", source = "teacher.id")
    @Mapping(target = "teacherName", source = "teacher.username")
    LessonDTO toDto(Lesson lesson);

    // Convert LessonDTO to Lesson entity
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Lesson toEntity(LessonDTO lessonDTO);

    // Update Lesson entity from LessonDTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateLessonFromDto(LessonDTO lessonDTO, @MappingTarget Lesson lesson);

    // Set default values after mapping
    @AfterMapping
    default void setDefaultValues(@MappingTarget Lesson lesson) {
        if (lesson.getMaxStudents() == null) {
            lesson.setMaxStudents(10); // Default Value
        }
    }
}