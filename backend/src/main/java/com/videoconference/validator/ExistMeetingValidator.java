package com.videoconference.validator;

import com.videoconference.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class ExistMeetingValidator implements ConstraintValidator<ExistMeeting, UUID> {
    @Autowired
    private MeetingService meetingService;
    @Override
    public void initialize(ExistMeeting constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UUID meetingId, ConstraintValidatorContext constraintValidatorContext) {
        return meetingService.existMeetingByMeetingId(meetingId);
    }
}
