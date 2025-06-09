package com.flaco.skateschool.model;

import com.flaco.skateschool.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity // Marks this class as a JPA entity
@Table(name = "users") // Specifies the table name in the database
@Inheritance(strategy = InheritanceType.JOINED) // Defines inheritance strategy for subclasses
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class) // Enables JPA auditing for this entity

public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50) // Configures column constraints
    private String username;

    @Column(nullable = false) // Marks this column as non-nullable
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @CreatedDate // Automatically sets the creation date
    @Column(updatable = false) // Prevents this field from being updated after creation
    private LocalDateTime createdAt;

    @LastModifiedDate // Automatically updates the last modified date
    private LocalDateTime updatedAt;

    // Default value for the active status
    private boolean active = true;
}