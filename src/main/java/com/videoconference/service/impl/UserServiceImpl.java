package com.videoconference.service.impl;

import com.videoconference.dto.users.ApiError;
import com.videoconference.dto.users.CreateUserDTO;
import com.videoconference.entity.User;
import com.videoconference.exception.ApiException;
import com.videoconference.repository.UserRepository;
import com.videoconference.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.videoconference.constant.ErrorConstant.EMAIL_EXISTED;
import static com.videoconference.constant.ErrorConstant.USERNAME_EXISTED;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Integer createUser(CreateUserDTO createUserDTO) {
        //mapping to user
        User user = new User();
        user.setUsername(createUserDTO.getUsername());
        user.setEmail(createUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        return userRepository.save(user).getUserId();
    }

    @Override
    public boolean isExistUsername(String username) {
        return userRepository.findFirstByUsernameOrEmail(username) != null;
    }

    @Override
    public boolean isExistEmail(String email) {
        return userRepository.findFirstByUsernameOrEmail(email) != null;
    }
}
