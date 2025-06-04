package com.flaco.skateschool.mapper;

import com.flaco.skateschool.dto.LessonDTO;
import com.flaco.skateschool.model.Lesson;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {TeacherMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LessonMapper {

    @Mapping(target = "teacherId", source = "teacher.id")
    @Mapping(target = "teacherName", source = "teacher.username")
    LessonDTO toDto(Lesson lesson);

    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Lesson toEntity(LessonDTO lessonDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateLessonFromDto(LessonDTO lessonDTO, @MappingTarget Lesson lesson);

    @AfterMapping
    default void setDefaultValues(@MappingTarget Lesson lesson) {
        if (lesson.getMaxStudents() == null) {
            lesson.setMaxStudents(10); // Default Value
        }
    }
}