package com.videoconference.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VerificationNotFoundException extends RuntimeException {
    public VerificationNotFoundException() {
        super("Token isn't found");
    }
}
