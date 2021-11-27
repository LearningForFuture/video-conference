package com.videoconference.converter;

import com.videoconference.dto.meeting.MeetingDTO;
import com.videoconference.entity.Meeting;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MeetingConverter {
    public Meeting toEntity(MeetingDTO meetingDTO) {
        Meeting meeting = new Meeting();
        meeting.setMeetingName(meetingDTO.getMeetingName());;
        meeting.setStartedAt(meetingDTO.getStartedAt());
        return meeting;
    }

    public MeetingDTO toDTO(Meeting meeting) {
        MeetingDTO meetingDTO = new MeetingDTO();
        meetingDTO.setMeetingId(meeting.getMeetingId());
        meetingDTO.setMeetingName(meeting.getMeetingName());
        meetingDTO.setCreatedBy(meeting.getCreatedBy().getUserId());
        meetingDTO.setFinishedAt(meeting.getFinishedAt());
        meetingDTO.setStartedAt(meeting.getStartedAt());
        meetingDTO.setRoomId(meeting.getRoom().getRoomId());
        meetingDTO.setParticipantMeetings(new ArrayList<>(meeting.getParticipantMeetings()));
        return meetingDTO;
    }
}
