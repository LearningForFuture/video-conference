package com.videoconference.validator;

import com.videoconference.dto.message.SignalMessage;
import com.videoconference.service.ParticipantMeetingService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class ExistParticipantMeetingValidator implements ConstraintValidator<ExistParticipantMeeting, Object> {
    @Autowired
    private ParticipantMeetingService participantMeetingService;

    @Override
    public void initialize(ExistParticipantMeeting constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (o instanceof SignalMessage) {
            String meetingId = ((SignalMessage) o).getMeetingId();
            String peerId = ((SignalMessage) o).getPeerId();
            String sender = ((SignalMessage) o).getSender();
            if (peerId != null) {
                return participantMeetingService
                        .isExistParticipantByMeetingIdAndParticipantId(UUID.fromString(meetingId),
                                Integer.valueOf(peerId));
            }
            else if (sender != null) {
                return participantMeetingService
                        .isExistParticipantByMeetingIdAndParticipantId(UUID.fromString(meetingId),
                                Integer.valueOf(sender));
            }
        }
        return true;
    }
}
