package com.soundtracker.backend.repository;

import com.soundtracker.backend.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
    void deleteUserByEmail(String email);
    Boolean existsUserByEmail(String email);
    Boolean existsUserByUsername(String username);
}
