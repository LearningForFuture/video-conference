package com.videoconference.security;

import com.videoconference.exception.UserNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class CookieRequestFilter {
    public String getUserId(HttpServletRequest request) {
        Optional<Cookie> user_id = Stream.of(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(cookie -> "user_id".equals(cookie.getName()))
                .findFirst();
        user_id.orElseThrow(() -> new UserNotFoundException());
        return user_id.get().getValue();
    }
}
