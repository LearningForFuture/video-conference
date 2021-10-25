package com.videoconference.service;

import com.videoconference.dto.users.CreateUserDTO;

public interface UserService {
    Integer createUser(CreateUserDTO createUserDTO);

    boolean isExistUsername(String username);

    boolean isExistEmail(String email);
}
