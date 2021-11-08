package com.videoconference.repository;

import com.videoconference.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.username = :usernameOrEmail or u.email = :usernameOrEmail")
    Optional<User> findFirstByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findFirstByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findFirstByEmail(@Param("email") String email);

    Optional<User> findFirstByUserId(Integer userId);
}
