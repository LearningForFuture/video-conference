package com.videoconference.service.impl;

import com.videoconference.converter.ParticipantRoomConverter;
import com.videoconference.converter.UserConverter;
import com.videoconference.dto.participantRoom.ParticipantRoomDTO;
import com.videoconference.entity.ParticipantRoom;

import com.videoconference.repository.ParticipantRoomRepository;
import com.videoconference.service.ParticipantRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantRoomServiceImpl implements ParticipantRoomService {
    @Autowired
    private ParticipantRoomRepository participantRoomRepository;

    @Autowired
    private ParticipantRoomConverter participantRoomConverter;

    @Autowired
    private UserConverter userConverter;

    @Override
    public List<ParticipantRoomDTO> getParticipantRoomByRoomId(Integer roomId) {
        return participantRoomRepository.findByRoomId(roomId)
                .stream().map(participantRoom -> {
                    ParticipantRoomDTO participantRoomDTO = participantRoomConverter.toDTO(participantRoom);
                    participantRoomDTO.setParticipant(userConverter.toDTO(participantRoom.getParticipant()));
                    return participantRoomDTO;
                }).collect(Collectors.toList());
    }
}
