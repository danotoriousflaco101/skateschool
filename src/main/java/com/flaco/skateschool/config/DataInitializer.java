package com.flaco.skateschool.config;

import com.flaco.skateschool.model.*;
import com.flaco.skateschool.repository.*;
import com.flaco.skateschool.enums.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final LessonRepository lessonRepository;
    private final BookingRepository bookingRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor injection for dependencies
    public DataInitializer(UserRepository userRepository, StudentRepository studentRepository,
                           TeacherRepository teacherRepository, LessonRepository lessonRepository,
                           BookingRepository bookingRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.lessonRepository = lessonRepository;
        this.bookingRepository = bookingRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        logger.info("Starting data initialization...");

        // Check if data already exists to prevent duplicate initialization
        if (userRepository.count() > 0) {
            logger.info("Data already initialized, skipping...");
            return;
        }

        try {
            // Create admin user for db population
            Teacher admin = new Teacher();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("adminpass"));
            admin.setRole(Role.ADMIN);
            admin.setSpecialty("Administration");
            admin.setYearsExperience(10);
            userRepository.save(admin);
            logger.info("Admin user created successfully");

            // Create students for db population
            Student student1 = new Student();
            student1.setUsername("student1");
            student1.setEmail("student1@example.com");
            student1.setPassword(passwordEncoder.encode("studentpass"));
            student1.setRole(Role.STUDENT);
            student1.setSkateStyle(SkateStyle.PARK);
            student1.setSkillLevel(SkillLevel.BEGINNER);
            studentRepository.save(student1);
            logger.info("Student created successfully");

            // Create teachers for db population
            Teacher teacher1 = new Teacher();
            teacher1.setUsername("teacher1");
            teacher1.setEmail("teacher1@example.com");
            teacher1.setPassword(passwordEncoder.encode("teacherpass"));
            teacher1.setRole(Role.TEACHER);
            teacher1.setSpecialty("Street Skating");
            teacher1.setYearsExperience(5);
            teacherRepository.save(teacher1);
            logger.info("Teacher created successfully");

            // Create lessons for db population
            Lesson lesson1 = new Lesson();
            lesson1.setTitle("Park Skating for Beginners");
            lesson1.setDescription("Learn the basics of park skating");
            lesson1.setTeacher(teacher1);
            lesson1.setStartTime(LocalDateTime.now().plusDays(1));
            lesson1.setEndTime(LocalDateTime.now().plusDays(1).plusHours(1));
            lesson1.setSkateStyle(SkateStyle.PARK);
            lesson1.setSkillLevel(SkillLevel.BEGINNER);
            lesson1.setMaxStudents(5);
            lessonRepository.save(lesson1);
            logger.info("Lesson created successfully");

            // Create bookings for db population
            Booking booking1 = new Booking();
            booking1.setStudent(student1);
            booking1.setLesson(lesson1);
            booking1.setStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(booking1);
            logger.info("Booking created successfully");

            logger.info("Data initialization completed successfully");
        } catch (Exception e) {
            logger.error("Error during data initialization", e);
            throw new RuntimeException("Data initialization failed", e);
        }
    }
}