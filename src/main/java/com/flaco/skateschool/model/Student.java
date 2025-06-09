package com.flaco.skateschool.model;

import com.flaco.skateschool.enums.SkateStyle;
import com.flaco.skateschool.enums.SkillLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
public class Student extends User {

    @Enumerated(EnumType.STRING) // Student's Skate style like STREET, VERT, FREESTYLE..
    private SkateStyle skateStyle;

    @Enumerated(EnumType.STRING) // Skill level of the student
    private SkillLevel skillLevel;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Booking> bookings;
}