package com.videoconference.service;

import com.videoconference.dto.participantRoom.ParticipantRoomDTO;

import java.util.List;

public interface ParticipantRoomService {
    List<ParticipantRoomDTO> getParticipantRoomByRoomId(Integer roomId);
}
