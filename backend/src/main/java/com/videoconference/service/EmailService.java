package com.videoconference.service;

public interface EmailService {
    void send(String subject, String body, String recipientAddress);
}
