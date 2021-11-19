package com.videoconference.converter;

import com.videoconference.dto.users.UserResponse;
import com.videoconference.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class UserConverter {
    public UserResponse toDTO(User user) {
        UserResponse users = new UserResponse();
        users.setUserId(user.getUserId());
        users.setFullName(user.getFullName());
        users.setCreatedAt(user.getCreatedAt());
        users.setUpdatedAt(user.getUpdatedAt());
        users.setRoles(new ArrayList<>(user.getRoles()));
        return users;
    }
}
