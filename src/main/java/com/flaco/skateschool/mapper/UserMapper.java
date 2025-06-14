package com.flaco.skateschool.mapper;

import com.flaco.skateschool.dto.UserDTO;
import com.flaco.skateschool.enums.Role;
import com.flaco.skateschool.model.Student;
import com.flaco.skateschool.model.Teacher;
import com.flaco.skateschool.model.User;
import org.mapstruct.*;

// Configure MapStruct mapper
@Mapper(componentModel = "spring",
        uses = {StudentMapper.class, TeacherMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    // Convert User entity to UserDTO
    @Mapping(target = "role", expression = "java(user.getRole() != null ? user.getRole().name() : null)")
    UserDTO toDto(User user);

    // Convert UserDTO to User entity
    default User toEntity(UserDTO dto) {
        if (dto == null || dto.getRole() == null) {
            throw new IllegalArgumentException("UserDTO and role cannot be null");
        }

        User user;
        try {
            Role role = Role.valueOf(dto.getRole().toUpperCase());
            if (role == Role.STUDENT) {
                user = new Student();
            } else if (role == Role.TEACHER) {
                user = new Teacher();
            } else {
                throw new IllegalArgumentException("Invalid role value: " + dto.getRole());
            }
            user.setRole(role);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role value: " + dto.getRole());
        }

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());

        if (user.getUsername() == null || user.getEmail() == null) {
            throw new IllegalStateException("Username and email are required");
        }

        return user;
    }

    // Update User entity from UserDTO, ignoring role
    @Mapping(target = "role", ignore = true)
    void updateUserFromDto(UserDTO userDTO, @MappingTarget User user);

    // Update user role after mapping
    @AfterMapping
    default void updateUserRole(UserDTO userDTO, @MappingTarget User user) {
        if (userDTO.getRole() != null) {
            try {
                Role role = Role.valueOf(userDTO.getRole().toUpperCase());
                user.setRole(role);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid role value: " + userDTO.getRole());
            }
        }
    }
}