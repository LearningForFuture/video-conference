package com.videoconference.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ParticipantRoomNotFoundExcepton extends RuntimeException{
    public ParticipantRoomNotFoundExcepton() {
        super("User don't join in this room");
    }
}
