package com.videoconference.controller;

import com.videoconference.dto.users.PasswordResetDTO;
import com.videoconference.dto.users.ResendRegistration;
import com.videoconference.entity.User;
import com.videoconference.event.OnPasswordForgotEvent;
import com.videoconference.event.OnRegistrationCompleteEvent;
import com.videoconference.security.JwtRequest;
import com.videoconference.security.JwtRequestFilter;
import com.videoconference.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Stream;

@Controller
@SecurityRequirement(name = "bearerAuth")
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
