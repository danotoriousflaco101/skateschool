package com.flaco.skateschool.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Teacher extends User {
    private String specialty; // STREET, VERT, FREESTYLE..
    private Integer yearsExperience; // Years of teaching experience

    // One-to-many relationship with Lesson entity
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true) // Teacher has many lessons
    private List<Lesson> lessons = new ArrayList<>(); // List of lessons taught by the teacher
}