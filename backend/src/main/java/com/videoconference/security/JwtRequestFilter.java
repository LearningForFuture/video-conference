package com.videoconference.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public static final String COOKIE_NAME = "access_token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

//        final String bearerHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (bearerHeader == null || bearerHeader.isEmpty() || !bearerHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }

//        final String token = bearerHeader.split(" ")[1].trim();
        Optional<Cookie> cookieToken = Stream.of(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(cookie -> COOKIE_NAME.equals(cookie.getName()))
                .findFirst();
        if (!cookieToken.isPresent()) {
            filterChain.doFilter(request, response);
            return;
        }
        final String token = cookieToken.get().getValue();

        // Get user identity and set it on spring security context
        UserDetails userDetails = this.userDetailsService.  loadUserByUsername(jwtTokenUtil.getUsernameFromToken(token));
        // get jwt token and validate
        if (!jwtTokenUtil.validateToken(token, userDetails)) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ? List.of() : userDetails.getAuthorities());

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

}