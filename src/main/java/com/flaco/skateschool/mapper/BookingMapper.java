package com.flaco.skateschool.mapper;

import com.flaco.skateschool.dto.BookingDTO;
import com.flaco.skateschool.model.Booking;
import com.flaco.skateschool.model.Lesson;
import com.flaco.skateschool.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    // Convert Booking entity to BookingDTO
    @Mapping(source = "lesson.id", target = "lessonId")
    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "lesson", target = "lessonTitle", qualifiedByName = "mapLessonTitle")
    @Mapping(source = "student", target = "studentName", qualifiedByName = "mapStudentName")
    BookingDTO toDto(Booking booking);

    // Convert BookingDTO to Booking entity
    @Mapping(source = "lessonId", target = "lesson")
    @Mapping(source = "studentId", target = "student")
    Booking toEntity(BookingDTO bookingDTO);

    // Create a Lesson entity with only the ID set
    default Lesson mapLesson(Long lessonId) {
        if (lessonId == null) {
            return null;
        }
        Lesson lesson = new Lesson();
        lesson.setId(lessonId);
        return lesson;
    }

    // Create a Student entity with only the ID set
    default Student mapStudent(Long studentId) {
        if (studentId == null) {
            return null;
        }
        Student student = new Student();
        student.setId(studentId);
        return student;
    }

    // Extract the title from a Lesson entity
    @Named("mapLessonTitle")
    default String mapLessonTitle(Lesson lesson) {
        return lesson != null ? lesson.getTitle() : null;
    }

    // Extract the username from a Student entity
    @Named("mapStudentName")
    default String mapStudentName(Student student) {
        return student != null ? student.getUsername() : null;
    }
}