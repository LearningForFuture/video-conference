package com.videoconference.service;

import com.videoconference.dto.meeting.MeetingDTO;
import com.videoconference.dto.message.SignalMessage;

public interface MeetingService {
    MeetingDTO createMeeting(MeetingDTO meetingDTO);
    MeetingDTO updateMeeting(MeetingDTO meetingDTO);

    void joinMeeting(String meeting_id, SignalMessage signalMessage, String sessionId);
    void relayICECandidate(String meeting_id, SignalMessage signalMessage, String sessionId);
    void relaySessionDescription(String meeting_id, SignalMessage signalMessage, String sessionId);
    void removePeer(String meeting_id, SignalMessage signalMessage, String sessionId);
}
