package com.videoconference.controller;

import com.videoconference.dto.users.PasswordResetDTO;
import com.videoconference.dto.users.ResendRegistration;
import com.videoconference.entity.User;
import com.videoconference.event.OnPasswordForgotEvent;
import com.videoconference.event.OnRegistrationCompleteEvent;
import com.videoconference.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/user/{username}")
    @ResponseBody
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    @ResponseBody
    public ResponseEntity<?> forgotPassword(@RequestBody @Valid ResendRegistration resendRegistration,
                                            HttpServletRequest request) {
        User user = userService.getUserByEmail(resendRegistration.getEmail());
        System.out.println(user.getEmail());
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnPasswordForgotEvent(user, appUrl));
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/reset-password/{token}")
    @ResponseBody
    public ResponseEntity<?> resetPassword(@RequestBody @Valid PasswordResetDTO passwordResetDTO,
                                           @PathVariable("token") String token) {
        userService.resetPassword(passwordResetDTO, token);
        return ResponseEntity.ok("OK");
    }
}
