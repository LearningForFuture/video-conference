package com.videoconference.controller;

import com.videoconference.dto.users.CreateUserDTO;
import com.videoconference.dto.users.OnRegistrationCompleteEvent;
import com.videoconference.dto.users.ResendRegistration;
import com.videoconference.entity.User;
import com.videoconference.security.JwtRequest;
import com.videoconference.security.JwtResponse;
import com.videoconference.security.JwtTokenUtil;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@CrossOrigin
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);
    @Autowired
    UserService userService;
    @Autowired
    ApplicationEventPublisher eventPublisher;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        Authentication authentication = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        final String token = jwtTokenUtil.generateToken(userDetails);

        logger.info(userDetails.getUsername() + " login success");
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> register(@Valid @RequestBody CreateUserDTO createUserDTO,
                                      HttpServletRequest request) {
        User registered = userService.createUser(createUserDTO);

        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, appUrl));
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/register/registration-confirm/{token}")
    @ResponseBody
    public ResponseEntity<?> confirmRegistration(@PathVariable("token") String token) {
        userService.confirmRegistration(token);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/register/resend-registration-confirm")
    @ResponseBody
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
