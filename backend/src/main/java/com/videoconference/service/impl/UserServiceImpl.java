package com.videoconference.service.impl;

import com.videoconference.dto.users.CreateUserDTO;
import com.videoconference.dto.users.PasswordResetDTO;
import com.videoconference.dto.users.RequestUserDTO;
import com.videoconference.dto.users.UserDTO;
import com.videoconference.entity.Role;
import com.videoconference.entity.User;
import com.videoconference.entity.VerificationToken;
import com.videoconference.exception.ExpiredTokenException;
import com.videoconference.exception.UserNotFoundException;
import com.videoconference.exception.VerificationNotFoundException;
import com.videoconference.repository.UserRepository;
import com.videoconference.repository.VerificationTokenRepository;
import com.videoconference.service.UserService;
import com.videoconference.util.PaginationAndSortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

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
                .orElseThrow(() -> new UserNotFoundException());
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new UserNotFoundException());
        return user;
    }

    @Override
    public Page<UserDTO> getUsers(int page, int size, String[] sort, String keyword) {
        Pageable pageable = PaginationAndSortUtil.create(page, size, sort);

        Page<User> pageRoom;

        if (keyword == null || keyword.isBlank()) {
            pageRoom = userRepository.findAll(pageable);
        } else {
            pageRoom = userRepository.search(keyword, pageable);
        }

        List<UserDTO> roomDTOs = mapList(pageRoom.getContent());

        return new PageImpl<>(roomDTOs, pageable, pageRoom.getTotalElements());
    }

    private Timestamp calExpiredDate(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime.plusMinutes(VerificationToken.EXPIRATION));
    }

    @Override
    public UserDTO createUserHasFullInfo(RequestUserDTO requestUserDTO) {
        User user = new User();
        user.setFullName(requestUserDTO.getFullName());
        user.setUsername(requestUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(requestUserDTO.getPassword()));
        user.setEmail(requestUserDTO.getEmail());
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        user.setEnabled(true);

        Set<Role> roles = new HashSet<>();
        for (Integer roleId : requestUserDTO.getRoleId())
            roles.add(new Role().setRoleId(roleId));
        user.setRoles(roles);

        User newUser = userRepository.save(user);
        return map(newUser);
    }

    @Override
    public UserDTO getUserByUserId(Integer userId) {
        User user = userRepository.findFirstByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException());
        return map(user);
    }

    @Override
    public UserDTO updateUser(Integer userId, RequestUserDTO requestUserDTO) {
        User user = userRepository.findFirstByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException());
        user.setFullName(requestUserDTO.getFullName());
        user.setUsername(requestUserDTO.getUsername());

        if (requestUserDTO.getPassword() != null) {
            String hash = passwordEncoder.encode(requestUserDTO.getPassword());
            if (user.getPassword().equals(hash))
                user.setPassword(hash);
        }

        user.setEmail(requestUserDTO.getEmail());
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        Set<Role> roles = new HashSet<>();
        for (Integer roleId : requestUserDTO.getRoleId())
            roles.add(new Role().setRoleId(roleId));
        user.setRoles(roles);

        User updatedUser = userRepository.save(user);
        return map(updatedUser);
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepository.findFirstByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException());
        userRepository.delete(user);
    }
    @Override
    public boolean isExistUserId(Integer userId) {
        return userRepository.findFirstByUserId(userId).isPresent();
    }

    private UserDTO map(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId())
                .setUsername(user.getUsername())
                .setFullName(user.getFullName())
                .setEmail(user.getEmail())
                .setEnabled(user.isEnabled())
                .setCreatedAt(user.getCreatedAt())
                .setUpdatedAt(user.getUpdatedAt())
                .setRoles(user.getRoles());
        return userDTO;
    }

    private List<UserDTO> mapList(List<User> users) {
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(map(user));
        }
        return userDTOs;
    }

}
