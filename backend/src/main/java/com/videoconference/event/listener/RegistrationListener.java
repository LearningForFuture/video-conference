package com.videoconference.event.listener;

import com.videoconference.entity.User;
import com.videoconference.event.OnRegistrationCompleteEvent;
import com.videoconference.service.EmailService;
import com.videoconference.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private UserService service;
    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    public void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl
                = event.getAppUrl() + "/register/registration-confirm/" + token;
        String body = "\r\n" + "http://tankhanh.tk:3000" + confirmationUrl;

        emailService.send(subject, body, recipientAddress);
    }
}