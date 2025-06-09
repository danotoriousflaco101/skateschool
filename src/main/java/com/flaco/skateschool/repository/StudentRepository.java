package com.flaco.skateschool.repository;

import com.flaco.skateschool.model.Student;
import com.flaco.skateschool.enums.SkateStyle;
import com.flaco.skateschool.enums.SkillLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findBySkateStyle(SkateStyle skateStyle); // Fetch students by skate style

    List<Student> findBySkillLevel(SkillLevel skillLevel); // Fetch students by skill level
}