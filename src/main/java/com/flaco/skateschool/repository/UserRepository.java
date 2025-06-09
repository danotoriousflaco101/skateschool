package com.flaco.skateschool.repository;

import com.flaco.skateschool.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    Page<User> findAllByActiveTrue(Pageable pageable);

    @Modifying
    @Query("UPDATE User u SET u.active = ?2 WHERE u.id = ?1")
    void updateUserActiveStatus(Long id, boolean active);

}