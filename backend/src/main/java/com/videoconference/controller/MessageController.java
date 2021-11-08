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
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MeetingStorage.class);

    private String currentJoinedMeeting = null;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{meeting_id}")
    @CrossOrigin(origins = "http://localhost:8080")
    public void sendMessage(@DestinationVariable String meeting_id, @Payload SignalMessage signalMessage,
                            SimpMessageHeaderAccessor headerAccessor) {
        try {
            String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
            System.out.println("handle send message: " + signalMessage + " to meeting: " + meeting_id);
//            String sender = headerAccessor.getUser().getName();
            switch (signalMessage.getType()) {
                case CHAT:{
                    break;
                }
                case JOIN:{
                    logger.info("user is " + headerAccessor.getUser().getName());
                    joinMeetingChat(meeting_id, signalMessage, sessionId);
                    break;
                }
                case RELAY_ICE_CANDIDATE: {
                    relayICECandidate(meeting_id, signalMessage, sessionId);
                    break;
                }
                case RELAY_SESSION_DESCRIPTION: {
                    relaySessionDescription(meeting_id, signalMessage, sessionId);
                }
                case LEAVE: {
                    break;
                }
                case REMOVE_PEER: {
                    removePeer(meeting_id, signalMessage, sessionId);
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

    public void joinMeetingChat(String meeting_id, SignalMessage signalMessage, String sessionId) {
        try {
            if (meeting_id.equals(currentJoinedMeeting)) {
                logger.warn("[Warning] " + sessionId + " already joined " + meeting_id);
                return;
            }

            this.currentJoinedMeeting = sessionId;

            boolean isMeetingExists =  MeetingStorage.getInstance().getMeetings().containsKey(meeting_id);
            if (!isMeetingExists) {
                MeetingStorage.getInstance().setMeetings(meeting_id);
            }

//            addPeerTo(meeting_id, sessionId);
            addPeerTo(meeting_id, sessionId, signalMessage.getSender());

            boolean isUserExists = UserStorage.getInstance().isUserExist(signalMessage.getSender());
            if (!isUserExists) {
                UserStorage.getInstance().setUsers(signalMessage.getSender());
            }
            MeetingStorage.getInstance().getMeetings().get(meeting_id).add(signalMessage.getSender());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void sendBroadCasting(String meeting_id, SignalMessage signalMessage) {
        MeetingStorage.getInstance().getMeetings().get(meeting_id).stream()
                .filter(key -> !key.equals(signalMessage.getSender())).collect(Collectors.toSet())
                .forEach(peerId -> {
            simpMessagingTemplate.convertAndSendToUser(peerId, "/topic/messages/" + meeting_id, signalMessage);
        });
    }

    public void addPeerTo(String meeting_id, String sessionId, String sender) {
        MessagePeer messagePeerSelf = new MessagePeer();
        messagePeerSelf.setShouldCreateOffer(true);
        messagePeerSelf.setType(SignalMessage.MessageType.ADD_PEER);

        MessagePeer messagePeerOther = new MessagePeer();
        messagePeerOther.setPeerId(sender);
        messagePeerOther.setShouldCreateOffer(false);
        messagePeerOther.setType(SignalMessage.MessageType.ADD_PEER);

        logger.info(MeetingStorage.getInstance().getMeetings().get(meeting_id).toString());

        MeetingStorage.getInstance().getMeetings().get(meeting_id).forEach(id -> {
            logger.info("send message add peer" + id );
            simpMessagingTemplate.convertAndSendToUser(id, "/topic/messages/" + meeting_id,
                    messagePeerOther);

            messagePeerSelf.setPeerId(id);
            simpMessagingTemplate.convertAndSendToUser(sender,"/topic/messages/" + meeting_id,
                    messagePeerSelf);
        });

    }

    public void relayICECandidate(String meeting_id, SignalMessage signalMessage, String sessionId) {
        try {
            String peerId = signalMessage.getPeerId();
            ICECandidate iceCandidate = signalMessage.getIceCandidate();
//            logger.debug(String.format("[%s] replay ICE candidate to %s : %s"), sessionId, peerId, iceCandidate.toString());
            sendToPeer(meeting_id, SignalMessage.MessageType.ICE_CANDIDATE, signalMessage, sessionId);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    public void relaySessionDescription(String meeting_id, SignalMessage signalMessage, String sessionId) {
        String peerId = signalMessage.getPeerId();
        Object sessionDescription = signalMessage.getSessionDescription();
//        logger.debug(String.format("[%s] replay SessionDescriptionProtocol to %s : %s"), sessionId, peerId,
//                sessionDescription.toString());
        sendToPeer(meeting_id, SignalMessage.MessageType.SESSION_DESCRIPTION, signalMessage, sessionId);
    }

    public void sendToPeer(String meeting_id, SignalMessage.MessageType messageType,
                           SignalMessage signalMessage, String sessionId) {
        String peerId = signalMessage.getPeerId();
        if (MeetingStorage.getInstance().getMeetings().get(meeting_id).contains(peerId)) {
            SignalMessage signalMessageResponse = new SignalMessage();
            signalMessageResponse.setPeerId(signalMessage.getSender());
            signalMessageResponse.setMeetingId(meeting_id);
            signalMessageResponse.setIceCandidate(signalMessage.getIceCandidate());
            signalMessageResponse.setSessionDescription(signalMessage.getSessionDescription());
            signalMessageResponse.setType(messageType);
            simpMessagingTemplate.convertAndSendToUser(peerId, "/topic/messages/" + meeting_id,
                    signalMessageResponse);
        }
    }

    public void removePeer(String meeting_id, SignalMessage signalMessage, String sessionId) {
        if (!MeetingStorage.getInstance().getMeetings().containsKey(meeting_id)) {
            logger.debug(String.format("[%] [Warning] not in %s", sessionId, meeting_id));
            return;
        }

        MessagePeer messagePeerSelf = new MessagePeer();
        messagePeerSelf.setType(SignalMessage.MessageType.REMOVE_PEER);

        MessagePeer messagePeerOther = new MessagePeer();
        messagePeerOther.setPeerId(signalMessage.getSender());
        messagePeerOther.setType(SignalMessage.MessageType.REMOVE_PEER);

        MeetingStorage.getInstance().getMeetings().get(meeting_id).forEach(id -> {
            simpMessagingTemplate.convertAndSendToUser(id, "/topic/messages/" + meeting_id,
                    messagePeerOther);

            messagePeerSelf.setPeerId(id);
            simpMessagingTemplate.convertAndSendToUser(signalMessage.getSender(), "/topic/messages/" + meeting_id,
                    messagePeerSelf);
        });

        MeetingStorage.getInstance().getMeetings().get(meeting_id).remove(signalMessage.getSender());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class MessagePeer {
        private String peerId;
        private boolean shouldCreateOffer;
        private SignalMessage.MessageType type;
    }
}
