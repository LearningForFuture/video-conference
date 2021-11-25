package com.videoconference.service;

import com.videoconference.dto.ParticipantMeeting.ParticipantMeetingDTO;

import java.util.UUID;

public interface ParticipantMeetingService {
    boolean isExistParticipantByMeetingIdAndParticipantId(UUID meetingId, Integer participantId);
    ParticipantMeetingDTO joinOrLeftMeeting(ParticipantMeetingDTO participantMeetingDTO);
    ParticipantMeetingDTO leftMeeting(ParticipantMeetingDTO participantMeetingDTO);
}
