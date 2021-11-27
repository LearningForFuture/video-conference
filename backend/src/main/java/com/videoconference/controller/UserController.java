package com.videoconference.controller;

import com.videoconference.dto.pagination.PaginationParams;
import com.videoconference.dto.pagination.PaginationResponse;
import com.videoconference.dto.users.PasswordResetDTO;
import com.videoconference.dto.users.RequestUserDTO;
import com.videoconference.dto.users.ResendRegistration;
import com.videoconference.dto.users.UserDTO;
import com.videoconference.entity.User;
import com.videoconference.event.OnPasswordForgotEvent;
import com.videoconference.event.OnRegistrationCompleteEvent;
import com.videoconference.security.JwtRequest;
import com.videoconference.security.JwtRequestFilter;
import com.videoconference.service.UserService;
import com.videoconference.util.PaginationAndSortUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/users")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getUsers(@Valid PaginationParams params) {
        Page<UserDTO> pageUserDTO = userService.getUsers(params.getPage(),
                params.getSize(), params.getSort(), params.getKeyword());

        PaginationResponse<UserDTO> response = PaginationAndSortUtil.map(pageUserDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUserHasFullInfo(@Valid @RequestBody RequestUserDTO requestUserDTO) {
        UserDTO userDTO = userService.createUserHasFullInfo(requestUserDTO);
        return ResponseEntity.ok(userDTO);
    }

//    @GetMapping("/users/{username}")
//    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
//        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
//    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Integer userId) {
        UserDTO userDTO = userService.getUserByUserId(userId);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Integer userId,
                                        @RequestBody RequestUserDTO requestUserDTO) {
        UserDTO userDTO = userService.updateUser(userId, requestUserDTO);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("Success");
    }


    @PostMapping("/reset-password")
    public ResponseEntity<?> forgotPassword(@RequestBody @Valid ResendRegistration resendRegistration,
                                            HttpServletRequest request) {
        User user = userService.getUserByEmail(resendRegistration.getEmail());
        System.out.println(user.getEmail());
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnPasswordForgotEvent(user, appUrl));
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid PasswordResetDTO passwordResetDTO,
                                           @PathVariable("token") String token) {
        userService.resetPassword(passwordResetDTO, token);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/sign-out")
    public ResponseEntity<Void> signOut(HttpServletRequest request) {
        SecurityContextHolder.clearContext();

        Optional<Cookie> authCookie = Stream.of(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(cookie -> JwtRequestFilter.COOKIE_NAME.equals(cookie.getName()))
                .findFirst();

        authCookie.ifPresent(cookie -> cookie.setMaxAge(0));
        return ResponseEntity.noContent().build();
    }
}
