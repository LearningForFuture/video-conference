package com.videoconference.validator;

import com.videoconference.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistRoomIdValidator implements ConstraintValidator<ExistRoomId, Integer> {
    @Autowired
    private RoomService roomService;

    @Override
    public void initialize(ExistRoomId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer roomId, ConstraintValidatorContext constraintValidatorContext) {
        return roomService.isExistRoomId(roomId);
    }
}
