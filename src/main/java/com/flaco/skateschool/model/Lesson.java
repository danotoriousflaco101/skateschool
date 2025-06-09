package com.flaco.skateschool.model;

import com.flaco.skateschool.enums.SkateStyle;
import com.flaco.skateschool.enums.SkillLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title; // Title of the lesson

    @Column(length = 500)
    private String description; // Description of the lesson

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SkillLevel skillLevel; // Skill level of the lesson

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SkateStyle skateStyle; // Type of lesson per skate style  (STREET, VERT, FREESTYLE...)

    @Column(nullable = false)
    private Integer maxStudents; // Maximum number of students allowed in the lesson

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    // Method to check if the lesson is full or not.
    public boolean isFull() {
        return bookings != null && bookings.size() >= maxStudents;
    }
}