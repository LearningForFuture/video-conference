package com.videoconference.converter;

import com.videoconference.dto.ParticipantMeeting.ParticipantMeetingDTO;
import com.videoconference.entity.ParticipantMeeting;
import org.springframework.stereotype.Component;

@Component
public class ParticipantMeetingConverter {
    public ParticipantMeetingDTO toDTO(ParticipantMeeting participantMeeting) {
        ParticipantMeetingDTO participantMeetingDTO = new ParticipantMeetingDTO();
        participantMeetingDTO.setMeetingId(participantMeeting.getId().getMeetingId());
        participantMeetingDTO.setParticipantId(participantMeeting.getId().getParticipantId());
        participantMeetingDTO.setJoinedAt(participantMeeting.getJoinedAt());
        participantMeetingDTO.setLeftAt(participantMeeting.getLeftAt());
        return participantMeetingDTO;
    }
}
