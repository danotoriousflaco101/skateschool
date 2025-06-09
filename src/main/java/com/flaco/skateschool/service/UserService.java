package com.flaco.skateschool.service;

import com.flaco.skateschool.dto.UserDTO;
import com.flaco.skateschool.model.User;
import com.flaco.skateschool.repository.UserRepository;
import com.flaco.skateschool.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // Retrieve the currently authenticated user
    public UserDTO getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userMapper.toDto(user);
    }

    // Retrieve a paginated list of all active users
    public Page<UserDTO> findAllActiveUsers(Pageable pageable) {
        return userRepository.findAllByActiveTrue(pageable).map(userMapper::toDto);
    }

    // Find a user by their ID
    public UserDTO findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userMapper.toDto(user);
    }

    // Create a new user
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    // Update an existing user's information
    @Transactional
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userMapper.updateUserFromDto(userDTO, existingUser);
        existingUser = userRepository.save(existingUser);
        return userMapper.toDto(existingUser);
    }

    // Activate a user account
    @Transactional
    public void activateUser(Long userId) {
        userRepository.updateUserActiveStatus(userId, true);
    }

    // Deactivate a user account
    @Transactional
    public void deactivateUser(Long userId) {
        userRepository.updateUserActiveStatus(userId, false);
    }

    // Delete a user account
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}