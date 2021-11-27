package com.videoconference.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Accessors(chain = true)
public class ParticipantRoomPK implements Serializable {
    private Integer roomId;
    private Integer participantId;
}
