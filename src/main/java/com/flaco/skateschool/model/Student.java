package com.flaco.skateschool.model;

import com.flaco.skateschool.enums.SkateStyle;
import com.flaco.skateschool.enums.SkillLevel;
import com.flaco.skateschool.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
public class Student extends User {

    @Enumerated(EnumType.STRING)
    private SkateStyle skateStyle;

    @Enumerated(EnumType.STRING)
    private SkillLevel skillLevel;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    // Set default role to STUDENT when creating a new Student object.
    public Student() {
        super();
        this.setRole(Role.STUDENT);
    }
}