package com.videoconference.validator;

import com.videoconference.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RoomCodeValidator implements ConstraintValidator<ExistRoomCode, String> {
    @Autowired
    private RoomService roomService;

    @Override
    public void initialize(ExistRoomCode constraintAnnotation) {
    }

    @Override
    public boolean isValid(String roomCode, ConstraintValidatorContext context) {
        return roomService.isExistRoom(roomCode);
    }
}
