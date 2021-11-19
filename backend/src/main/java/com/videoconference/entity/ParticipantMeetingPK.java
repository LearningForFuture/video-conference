package com.videoconference.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
public class ParticipantMeetingPK implements Serializable {
    @Column(name = "meeting_id")
//    private Integer meetingId;
    private UUID meetingId;
    @Column(name = "participant_id")
    private Integer participantId;
}
