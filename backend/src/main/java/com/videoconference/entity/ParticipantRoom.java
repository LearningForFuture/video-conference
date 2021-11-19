package com.videoconference.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "participant_room")
public class ParticipantRoom {
    @EmbeddedId
    private ParticipantRoomPK id = new ParticipantRoomPK();

    private Timestamp joinedAt;
    private Timestamp leftAt;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id", referencedColumnName = "roomId")
    private Room room;

    @ManyToOne
    @MapsId("participantId")
    @JoinColumn(name = "participant_id", referencedColumnName = "userId")
    private User participant;
}
