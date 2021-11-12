package com.videoconference.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "participant_room")
public class ParticipantRoom {
    @EmbeddedId
    private ParticipantRoomPK id;

    private Timestamp joinedAt;
    private Timestamp leftAt;
    private Boolean isAdmin;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId("roomId")
//    @JoinColumn(name = "room_id")
//    private Room room;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId("participantId")
//    @JoinColumn(name = "participant_id")
//    private User participant;
}
