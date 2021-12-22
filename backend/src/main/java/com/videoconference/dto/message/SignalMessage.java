package com.videoconference.dto.message;

import com.videoconference.validator.ExistParticipantMeeting;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ExistParticipantMeeting
public class SignalMessage {
    private MessageType type;
    private Object content;
    private String sender;
    @NotNull
    @NotEmpty(message = "meeting Id không được bỏ trống")
    private String meetingId;
    private ICECandidate iceCandidate;
    private Object sessionDescription;
    private String peerId;
    private boolean shouldCreateOffer;
    private MessageDTO message;
    private String peerName;

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
