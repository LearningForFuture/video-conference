package com.videoconference.controller;

import com.videoconference.dto.message.ICECandidate;
import com.videoconference.dto.message.SignalMessage;
import com.videoconference.storage.MeetingStorage;
import com.videoconference.storage.UserStorage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.Principal;
import java.util.stream.Collectors;

import static org.springframework.messaging.simp.SimpMessageHeaderAccessor.getUser;

@Controller
public class MessageController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendBroadCasting(String meeting_id, SignalMessage signalMessage) {
        MeetingStorage.getInstance().getMeetings().get(meeting_id).stream()
                .filter(key -> !key.equals(signalMessage.getSender())).collect(Collectors.toSet())
                .forEach(peerId -> {
                    simpMessagingTemplate.convertAndSendToUser(peerId, "/topic/messages/" + meeting_id, signalMessage);
                });
    }

}