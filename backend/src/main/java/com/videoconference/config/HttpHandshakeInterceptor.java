package com.videoconference.config;

import com.videoconference.security.JwtTokenUtil;
import com.videoconference.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static com.videoconference.security.JwtRequestFilter.COOKIE_NAME;

public class HttpHandshakeInterceptor implements HandshakeInterceptor {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession();
            attributes.put("sessionId", session.getId());
//            Optional<Cookie> cookieToken = Stream.of(Optional.ofNullable(servletRequest.getServletRequest().getCookies()).orElse(new Cookie[0]))
//                    .filter(cookie -> COOKIE_NAME.equals(cookie.getName()))
//                    .findFirst();
//            if (cookieToken.isPresent()) {
//                final String token = cookieToken.get().getValue();
//                UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtTokenUtil.getUsernameFromToken(token));
//                if (jwtTokenUtil.validateToken(token, userDetails)) {
//                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                            userDetails, null,
//                            userDetails == null ? List.of() : userDetails.getAuthorities());
//
//                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(servletRequest.getServletRequest()));
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                }
//            }
        }
        return true;
    }
}
