package com.videoconference.controller;

import com.videoconference.dto.message.MessageDTO;
import com.videoconference.dto.message.SignalMessage;
import com.videoconference.service.MessageService;
import com.videoconference.storage.MeetingStorage;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class MessageController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageService messageService;

    public void sendBroadCasting(String meeting_id, SignalMessage signalMessage) {
        MeetingStorage.getInstance().getMeetings().get(meeting_id).stream()
                .filter(key -> !key.equals(signalMessage.getSender())).collect(Collectors.toSet())
                .forEach(peerId -> {
                    simpMessagingTemplate.convertAndSendToUser(peerId,
                            "/topic/meeting/" + meeting_id + "/chat", signalMessage);
                });
    }

    @GetMapping("/rooms/{room-id}/meeting/{meeting-id}/messages")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<MessageDTO> getMessageByMeetingId(@PathVariable("room-id") Integer roomId,
                                                  @PathVariable("meeting-id") @NotBlank String meetingId) {
        return messageService.findMessageByMeetingId(UUID.fromString(meetingId));
    }

}