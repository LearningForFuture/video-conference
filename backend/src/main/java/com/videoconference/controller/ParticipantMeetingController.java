package com.videoconference.controller;

import com.videoconference.dto.ParticipantMeeting.ParticipantMeetingDTO;
import com.videoconference.service.ParticipantMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@RestController
public class ParticipantMeetingController {
    @Autowired
    private ParticipantMeetingService participantMeetingService;

    @PostMapping("/meeting/{meeting-id}/join-meeting")
    @PreAuthorize("hasAnyRole('USER', 'ADMN')")
    public ParticipantMeetingDTO joinMeeting(@PathVariable("meeting-id") @NotBlank String meetingId,
                                             @Valid @RequestBody ParticipantMeetingDTO participantMeetingDTO) {
        participantMeetingDTO.setMeetingId(UUID.fromString(meetingId));
        return participantMeetingService.joinOrLeftMeeting(participantMeetingDTO);
    }

    @PutMapping("/meeting/{meeting-id}/left-meeting")
    @PreAuthorize("hasAnyRole('USER', 'ADMN')")
    public ParticipantMeetingDTO leftMeeting(@PathVariable("meeting-id") @NotBlank String meetingId,
                                             @Valid @RequestBody ParticipantMeetingDTO participantMeetingDTO) {
        participantMeetingDTO.setMeetingId(UUID.fromString(meetingId));
        return participantMeetingService.joinOrLeftMeeting(participantMeetingDTO);
    }
}
