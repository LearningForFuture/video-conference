package com.videoconference.repository;

import com.videoconference.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.username = :usernameOrEmail or u.email = :usernameOrEmail")
    User findFirstByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);
}
