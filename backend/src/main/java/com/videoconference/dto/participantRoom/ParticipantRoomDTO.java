package com.videoconference.dto.participantRoom;

import com.videoconference.dto.users.UserResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class ParticipantRoomDTO {
    private Integer roomId;
    private Integer participantId;
    private UserResponse participant;
    private Timestamp joinedAt;
    private Timestamp leftAt;
    private Boolean isAdmin;
}
