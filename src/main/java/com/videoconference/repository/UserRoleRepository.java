package com.videoconference.repository;

import com.videoconference.entity.UsersRole;
import com.videoconference.entity.UsersRolePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UsersRole, UsersRolePK> {
}
