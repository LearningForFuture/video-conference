package com.videoconference.controller;

import com.videoconference.dto.pagination.PaginationParams;
import com.videoconference.dto.pagination.PaginationResponse;
import com.videoconference.dto.users.PasswordResetDTO;
import com.videoconference.dto.users.ResendRegistration;
import com.videoconference.dto.users.UserDTO;
import com.videoconference.entity.User;
import com.videoconference.event.OnPasswordForgotEvent;
import com.videoconference.service.UserService;
import com.videoconference.util.PaginationAndSortUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getUsers(@Valid PaginationParams params) {
        Page<UserDTO> pageUserDTO = userService.getUsers(params.getPage(),
                params.getSize(), params.getSort(), params.getKeyword());

        PaginationResponse<UserDTO> response = PaginationAndSortUtil.map(pageUserDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
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
}
