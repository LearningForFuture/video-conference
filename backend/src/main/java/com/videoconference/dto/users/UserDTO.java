package com.videoconference.dto.users;

import com.videoconference.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class UserDTO {
    private Integer userId;
    private String fullName;
    private String email;
    private String username;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean enabled;
    private Set<Role> roles;
}
