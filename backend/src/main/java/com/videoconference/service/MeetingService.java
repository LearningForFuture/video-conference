package com.videoconference.service;

import com.videoconference.dto.meeting.MeetingDTO;
import com.videoconference.dto.message.SignalMessage;

import java.util.List;
import java.util.UUID;

public interface MeetingService {
    MeetingDTO createMeeting(MeetingDTO meetingDTO);
    MeetingDTO updateMeeting(MeetingDTO meetingDTO);
    List<MeetingDTO> getMeetingByRoomId(Integer roomId);
    MeetingDTO findByMeetingId(UUID meetingId);
    boolean existMeetingByMeetingId(UUID meetingId);

    void joinMeeting(String meeting_id, SignalMessage signalMessage, String sessionId);
    void relayICECandidate(String meeting_id, SignalMessage signalMessage, String sessionId);
    void relaySessionDescription(String meeting_id, SignalMessage signalMessage, String sessionId);
    void removePeer(String meeting_id, SignalMessage signalMessage, String sessionId);
}
