package com.videoconference.service;

import com.videoconference.dto.users.CreateUserDTO;
import com.videoconference.dto.users.PasswordResetDTO;
import com.videoconference.dto.users.UserDTO;
import com.videoconference.entity.User;
import com.videoconference.entity.VerificationToken;
import org.springframework.data.domain.Page;

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

    Page<UserDTO> getUsers(int page, int size, String[] sort, String keyword);
}
