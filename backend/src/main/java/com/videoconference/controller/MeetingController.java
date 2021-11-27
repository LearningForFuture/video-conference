package com.videoconference.controller;

import com.videoconference.dto.meeting.MeetingDTO;
import com.videoconference.dto.message.SignalMessage;
import com.videoconference.security.CookieRequestFilter;
import com.videoconference.service.MeetingService;
import com.videoconference.storage.MeetingStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@RestController
@RequestMapping("/teams/room")
public class MeetingController {
    private static final Logger logger = LoggerFactory.getLogger(MeetingController.class);
    @Autowired
    private MeetingService meetingService;

    @Autowired
    private CookieRequestFilter cookieRequestFilter;

    @PostMapping("/{roomId}/meeting")
    public ResponseEntity<MeetingDTO> createMeeting(@PathVariable @NotBlank Integer roomId, @Valid @RequestBody MeetingDTO meetingDTO,
                                                    HttpServletRequest request) {
        String user_id = cookieRequestFilter.getUserId(request);
        meetingDTO.setCreatedBy(Integer.valueOf(user_id));
        meetingDTO.setRoomId(roomId);

        return ResponseEntity.status(HttpStatus.OK).body(meetingService.createMeeting(meetingDTO));
    }

    @PutMapping("/{roomId}/meeting/{meetingId}")
    public ResponseEntity<MeetingDTO>  updateMeeting(@PathVariable @NotBlank String roomId,
                                                     @PathVariable @NotBlank UUID meetingId,
                                                     @RequestBody MeetingDTO meetingDTO) {
        meetingDTO.setMeetingId(meetingId);
        meetingDTO.setRoomId(Integer.valueOf(roomId));
        return ResponseEntity.status(HttpStatus.OK).body(meetingService.updateMeeting(meetingDTO));
    }

    @MessageMapping("/meeting/{meeting_id}")
    @CrossOrigin(origins = "http://localhost:8080")
    public void sendMessage(@DestinationVariable String meeting_id, @Payload SignalMessage signalMessage,
                            SimpMessageHeaderAccessor headerAccessor) {
        try {
            String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
//            String sender = headerAccessor.getUser().getName();
            switch (signalMessage.getType()) {
                case CHAT:{
                    break;
                }
                case JOIN:{
                    logger.info("user is " + headerAccessor.getUser().getName());
                    meetingService.joinMeeting(meeting_id, signalMessage, sessionId);
                    break;
                }
                case RELAY_ICE_CANDIDATE: {
                    meetingService.relayICECandidate(meeting_id, signalMessage, sessionId);
                    break;
                }
                case RELAY_SESSION_DESCRIPTION: {
                    meetingService.relaySessionDescription(meeting_id, signalMessage, sessionId);
                }
                case LEAVE: {
                    break;
                }
                case REMOVE_PEER: {
                    meetingService.removePeer(meeting_id, signalMessage, sessionId);
                    break;
                }
                default: {
                    break;
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
