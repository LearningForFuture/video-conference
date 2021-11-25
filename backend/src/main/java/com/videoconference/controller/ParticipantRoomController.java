package com.videoconference.controller;

import com.videoconference.dto.participantRoom.ParticipantRoomDTO;
import com.videoconference.service.ParticipantRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
public class ParticipantRoomController {
    @Autowired
    private ParticipantRoomService participantRoomService;

    @GetMapping("/rooms/{roomId}/participant-room")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<ParticipantRoomDTO> getAllParticipantRoom(@PathVariable @NotBlank Integer roomId) {
        return participantRoomService.getParticipantRoomByRoomId(roomId);
    }
}
