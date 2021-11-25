package com.videoconference.controller;

import com.videoconference.dto.meeting.MeetingDTO;
import com.videoconference.dto.message.MessageDTO;
import com.videoconference.dto.message.SignalMessage;
import com.videoconference.dto.users.UserResponse;
import com.videoconference.security.CookieRequestFilter;
import com.videoconference.service.MeetingService;
import com.videoconference.service.MessageService;
import com.videoconference.service.UserService;
import com.videoconference.storage.MeetingStorage;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/teams/room")
@SecurityRequirement(name = "bearerAuth")
public class MeetingController {
    private static final Logger logger = LoggerFactory.getLogger(MeetingController.class);
    @Autowired
    private MeetingService meetingService;

    @Autowired
    private CookieRequestFilter cookieRequestFilter;

    @Autowired
    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserService userService;

    @GetMapping("/{roomId}/meeting")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<MeetingDTO> getAllMeetingInRoom(@PathVariable @NotBlank Integer roomId) {
        return meetingService.getMeetingByRoomId(roomId);
    }

    @GetMapping("/{room-id}/meeting/{meeting-id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public MeetingDTO getMeetingByMeetingId(@PathVariable("room-id") Integer roomId,
                                            @PathVariable("meeting-id") @NotBlank UUID meetingId)  {
        return meetingService.findByMeetingId(meetingId);
    }

    @PostMapping("/{roomId}/meeting")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MeetingDTO> createMeeting(@PathVariable @NotBlank Integer roomId, @Valid @RequestBody MeetingDTO meetingDTO,
                                                    HttpServletRequest request) {
        String user_id = cookieRequestFilter.getUserId(request);
        meetingDTO.setCreatedBy(Integer.valueOf(user_id));
        meetingDTO.setRoomId(roomId);

        return ResponseEntity.status(HttpStatus.OK).body(meetingService.createMeeting(meetingDTO));
    }

    @PutMapping("/{roomId}/meeting/{meetingId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MeetingDTO>  endMeeting(@PathVariable @NotBlank String roomId,
                                                     @PathVariable @NotBlank UUID meetingId,
                                                     @RequestBody MeetingDTO meetingDTO,
                                                     HttpServletRequest request) {
        String user_id = cookieRequestFilter.getUserId(request);
        meetingDTO.setCreatedBy(Integer.valueOf(user_id));
        meetingDTO.setMeetingId(meetingId);
        meetingDTO.setRoomId(Integer.valueOf(roomId));
        return ResponseEntity.status(HttpStatus.OK).body(meetingService.updateMeeting(meetingDTO));
    }

    @GetMapping("/{roomId}/meeting/{meetingId}/user-online-status")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<UserResponse> getUsersInCall(@PathVariable @NotBlank String meetingId) {
        int[] userIds = MeetingStorage.getInstance().getMeetings().get(meetingId)
                .stream().mapToInt(Integer::parseInt).toArray();
        return userService.getUserByUserIds(userIds);
    }




    @MessageMapping("/meeting/{meeting_id}")
    @CrossOrigin(origins = "http://localhost:8080")
    public void sendMessage(@DestinationVariable String meeting_id, @Valid @Payload SignalMessage signalMessage,
                            SimpMessageHeaderAccessor headerAccessor) {
        try {
            String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
//            String sender = headerAccessor.getUser().getName();
            switch (signalMessage.getType()) {
                case CHAT:{
                    signalMessage.getMessage().setMeetingId(UUID.fromString(meeting_id));
                    MessageDTO message = messageService.addMessage(signalMessage.getMessage());
//                    simpMessagingTemplate.convertAndSendToUser(message.getSenderId().toString(),
//                            "/topic/meeting/" + meeting_id + "/chat", message);
                    simpMessagingTemplate.convertAndSend("/topic/meeting/" + meeting_id + "/chat", message);
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
