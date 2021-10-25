package com.videoconference.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Embeddable
public class ParticipantRoomPK implements Serializable {
    private Integer roomId;
    private Integer participantId;
}
