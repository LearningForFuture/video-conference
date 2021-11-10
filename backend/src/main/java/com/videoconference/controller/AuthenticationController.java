package com.videoconference.controller;

import com.videoconference.security.JwtRequest;
import com.videoconference.security.JwtResponse;
import com.videoconference.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${cookie.token_name}")
    private String cookieTokenName;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest,
                                                       HttpServletResponse response) throws Exception {
        Authentication authentication = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        final String token = jwtTokenUtil.generateToken(userDetails);

        // add cookie token per request
        Cookie cookie = new Cookie(cookieTokenName, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(5 * 60 * 60);
        response.addCookie(cookie);

        logger.info(userDetails.getUsername() + " login success");
        return ResponseEntity.ok(new JwtResponse(token));
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
