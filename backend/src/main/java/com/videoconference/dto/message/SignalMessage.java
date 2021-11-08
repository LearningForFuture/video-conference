package com.videoconference.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignalMessage {
    private MessageType type;
    private Object content;
    private String sender;
    private String meetingId;
    private ICECandidate iceCandidate;
    private Object sessionDescription;
    private String peerId;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE,
        ADD_PEER,
        REMOVE_PEER,
        ICE_CANDIDATE,
        SESSION_DESCRIPTION,
        RELAY_ICE_CANDIDATE,
        RELAY_SESSION_DESCRIPTION
    }
}
