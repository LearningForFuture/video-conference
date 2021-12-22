package com.videoconference.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ParticipantMeetingNotFound extends RuntimeException{
    public ParticipantMeetingNotFound() {
        super("Participant meeting not found");
    }
}
