package com.videoconference.dto.message;

import lombok.*;

@Getter
@Setter
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
    private boolean shouldCreateOffer;

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
