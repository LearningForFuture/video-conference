package com.videoconference.controller;

import com.videoconference.dto.users.CreateUserDTO;
import com.videoconference.dto.users.UserResponse;
import com.videoconference.event.OnRegistrationCompleteEvent;
import com.videoconference.dto.users.ResendRegistration;
import com.videoconference.entity.User;
import com.videoconference.security.*;
import com.videoconference.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
//@CrossOrigin(origins = "http://localhost:8080/")
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest,
                                                                  HttpServletResponse response) throws Exception {
        Authentication authentication = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

        final String token = jwtTokenUtil.generateToken(userDetails);

        Cookie cookieToken = new Cookie(JwtRequestFilter.COOKIE_NAME, token);
        Cookie cookieUserId = new Cookie("user_id", userDetails.getUser().getUserId().toString());
        Arrays.asList(cookieToken, cookieUserId).forEach(cookie -> {
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setMaxAge(60*60*5);
            cookie.setPath("/");
            response.addCookie(cookie);
        });

        userDetails.getUser().setToken(new JwtResponse(token));

        logger.info(userDetails.getUsername() + " login success");
        return ResponseEntity.status(200).body(userDetails.getUser());
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody CreateUserDTO createUserDTO,
                                      HttpServletRequest request) {
        User registered = userService.createUser(createUserDTO);

        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, appUrl));
        return ResponseEntity.status(200).body(registered);
    }

    @GetMapping("/register/registration-confirm/{token}")
    public ResponseEntity<String> confirmRegistration(@PathVariable("token") String token) {
        userService.confirmRegistration(token);
        return ResponseEntity.status(200).body("verify success");
    }

    @PostMapping("/register/resend-registration-confirm")
    public ResponseEntity<?> resendRegistration(@RequestBody ResendRegistration resendRegistration,
                                                HttpServletRequest request) {
        User user = userService.getUserByEmail(resendRegistration.getEmail());

        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, appUrl));
        return ResponseEntity.ok("OK");
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
