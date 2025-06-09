package com.flaco.skateschool.repository;

import com.flaco.skateschool.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    // This repository provides CRUD operations for Teacher entities.
    // It extends JpaRepository, which offers methods like:
    // - save(): for creating and updating teachers
    // - findById(): for retrieving a teacher by ID
    // - findAll(): for retrieving all teachers
    // - delete(): for removing a teacher
    // - count(): for getting the total number of teachers
}