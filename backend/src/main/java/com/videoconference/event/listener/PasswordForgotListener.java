package com.videoconference.event.listener;

import com.videoconference.entity.User;
import com.videoconference.event.OnPasswordForgotEvent;
import com.videoconference.service.EmailService;
import com.videoconference.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PasswordForgotListener implements
        ApplicationListener<OnPasswordForgotEvent> {
    @Autowired
    private UserService service;
    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(OnPasswordForgotEvent event) {
        this.forgotPasswordEmail(event);
    }

    public void forgotPasswordEmail(OnPasswordForgotEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Reset password";
        String confirmationUrl
                = event.getAppUrl() + "/reset-password/" + token;
        String body = "\r\n" + "http://localhost:8080" + confirmationUrl;

        emailService.send(subject, body, recipientAddress);
    }
}
