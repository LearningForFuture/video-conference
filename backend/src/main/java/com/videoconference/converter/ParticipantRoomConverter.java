package com.videoconference.converter;

import com.videoconference.dto.participantRoom.ParticipantRoomDTO;
import com.videoconference.entity.ParticipantRoom;
import org.springframework.stereotype.Component;

@Component
public class ParticipantRoomConverter {
    public ParticipantRoomDTO toDTO(ParticipantRoom participantRoom) {
        ParticipantRoomDTO participantRoomDTO = new ParticipantRoomDTO();
        participantRoomDTO.setRoomId(participantRoom.getId().getRoomId());
        participantRoomDTO.setParticipantId(participantRoom.getId().getParticipantId());
        participantRoomDTO.setIsAdmin(participantRoom.getIsAdmin());
        participantRoomDTO.setJoinedAt(participantRoom.getJoinedAt());
        participantRoomDTO.setLeftAt(participantRoom.getLeftAt());
        return participantRoomDTO;
    }
}
