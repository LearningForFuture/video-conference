package com.videoconference.service.impl;

import com.videoconference.dto.users.CreateUserDTO;
import com.videoconference.dto.users.PasswordResetDTO;
import com.videoconference.entity.Role;
import com.videoconference.entity.User;
import com.videoconference.entity.VerificationToken;
import com.videoconference.exception.ExpiredTokenException;
import com.videoconference.exception.UserNotFoundException;
import com.videoconference.exception.VerificationNotFoundException;
import com.videoconference.repository.UserRepository;
import com.videoconference.repository.VerificationTokenRepository;
import com.videoconference.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.videoconference.constant.SystemConstant.ROLE_USER;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    public User createUser(CreateUserDTO createUserDTO) {
        //mapping to user
        User user = new User();
        user.setUsername(createUserDTO.getUsername());
        user.setEmail(createUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        Set<Role> roles = new HashSet<>();
        roles.add(new Role().setRoleId(ROLE_USER));
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public void confirmRegistration(String token) {
        VerificationToken verificationToken = getVerificationToken(token);

        if (verificationToken.getExpiryDate().getTime() - (new Date().getTime()) <= 0) {
            throw new ExpiredTokenException();
        }

        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public boolean isExistUsername(String username) {
        return userRepository.findFirstByUsername(username).isPresent();
    }

    @Override
    public boolean isExistEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @Override
    public void createVerificationToken(User user, String token) {
        LocalDateTime localDateTime = LocalDateTime.now();
        VerificationToken myToken;
        Optional<VerificationToken> existedToken = tokenRepository.findFirstByUser_UserId(user.getUserId());

        if (existedToken.isPresent()) {
            myToken = existedToken.get();
            myToken.setToken(token);
        } else {
            myToken = new VerificationToken(token, user);
            myToken.setCreatedAt(Timestamp.valueOf(localDateTime));
        }

        myToken.setExpiryDate(calExpiredDate(localDateTime));
        tokenRepository.save(myToken);
    }

    @Override
    public void resetPassword(PasswordResetDTO passwordResetDTO, String token) {
        VerificationToken verificationToken = getVerificationToken(token);
        User user = userRepository.findFirstByUserId(verificationToken.getUser().getUserId())
                .orElseThrow(() -> new UserNotFoundException());

        user.setPassword(passwordEncoder.encode(passwordResetDTO.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getUser(String verificationToken) {
        return getVerificationToken(verificationToken).getUser();
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return tokenRepository.findByToken(verificationToken)
                .orElseThrow(() -> new VerificationNotFoundException());
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userRepository.findFirstByUsername(username)
                .orElseThrow(()  -> new UserNotFoundException());
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new UserNotFoundException());
        return user;
    }

    private Timestamp calExpiredDate(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime.plusMinutes(VerificationToken.EXPIRATION));
    }

    @Override
    public boolean isExistUserId(Integer userId) {
        return userRepository.findFirstByUserId(userId).isPresent();
    }
}
