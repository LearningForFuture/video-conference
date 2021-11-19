package com.videoconference.service;

import com.videoconference.dto.users.CreateUserDTO;
import com.videoconference.dto.users.PasswordResetDTO;
import com.videoconference.entity.User;
import com.videoconference.entity.VerificationToken;

import java.util.Optional;

public interface UserService {
    User createUser(CreateUserDTO createUserDTO);

    void confirmRegistration(String token);

    boolean isExistUsername(String username);

    boolean isExistEmail(String email);

    void createVerificationToken(User user, String token);

    void resetPassword(PasswordResetDTO passwordResetDTO, String token);

    User getUser(String verificationToken);

    VerificationToken getVerificationToken(String VerificationToken);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    boolean isExistUserId(Integer userId);
}
