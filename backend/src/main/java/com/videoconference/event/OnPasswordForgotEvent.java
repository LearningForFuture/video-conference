package com.videoconference.event;

import com.videoconference.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnPasswordForgotEvent extends ApplicationEvent {
    private String appUrl;
    private User user;

    public OnPasswordForgotEvent(
            User user, String appUrl) {
        super(user);

        this.user = user;
        this.appUrl = appUrl;
    }
}
