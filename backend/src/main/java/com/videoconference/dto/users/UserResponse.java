package com.videoconference.dto.users;

import com.videoconference.entity.Role;
import com.videoconference.security.JwtResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private Integer userId;
    private String fullName;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String password;
    private List<Role> roles;
    private JwtResponse token;
}
