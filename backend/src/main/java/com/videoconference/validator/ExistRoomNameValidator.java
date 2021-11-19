package com.videoconference.validator;

import com.videoconference.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistRoomNameValidator implements ConstraintValidator<ExistRoomName, String> {
    @Autowired
    private RoomService roomService;

    @Override
    public void initialize(ExistRoomName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String roomName, ConstraintValidatorContext constraintValidatorContext) {
        return !roomService.isExistRoomName(roomName);
    }
}
