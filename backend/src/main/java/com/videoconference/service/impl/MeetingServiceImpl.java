package com.videoconference.service.impl;

import com.videoconference.controller.MessageController;
import com.videoconference.converter.MeetingConverter;
import com.videoconference.dto.meeting.MeetingDTO;
import com.videoconference.dto.message.ICECandidate;
import com.videoconference.dto.message.SignalMessage;
import com.videoconference.entity.Meeting;
import com.videoconference.entity.Room;
import com.videoconference.entity.User;
import com.videoconference.exception.MeetingNotFoundException;
import com.videoconference.exception.RoomNotFoundException;
import com.videoconference.exception.UserNotFoundException;
import com.videoconference.repository.MeetingRepository;
import com.videoconference.repository.RoomRepository;
import com.videoconference.repository.UserRepository;
import com.videoconference.service.MeetingService;
import com.videoconference.storage.MeetingStorage;
import com.videoconference.storage.UserStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MeetingServiceImpl implements MeetingService {
    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingConverter meetingConverter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private static final Logger logger = LoggerFactory.getLogger(MeetingServiceImpl.class);

    @Override
    public MeetingDTO createMeeting(MeetingDTO meetingDTO) {
        User createBy = userRepository.findFirstByUserId(meetingDTO.getCreatedBy())
                .orElseThrow(() -> new UserNotFoundException());
        Room roomId = roomRepository.findById(meetingDTO.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException());

        meetingDTO.setStartedAt(Timestamp.valueOf(LocalDateTime.now()));
        Meeting meeting = meetingConverter.toEntity(meetingDTO);
        meeting.setCreatedBy(createBy);
        meeting.setRoom(roomId);
        return meetingConverter.toDTO(meetingRepository.save(meeting));
    }

    @Override
    public MeetingDTO updateMeeting(MeetingDTO meetingDTO) {

        Meeting meeting =  meetingRepository.findById(meetingDTO.getMeetingId())
                .orElseThrow(() -> new MeetingNotFoundException());
        Room roomId = roomRepository.findById(meetingDTO.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException());
        if (!meeting.getCreatedBy().getUserId().equals(meetingDTO.getCreatedBy())
                || !meeting.getRoom().getRoomId().equals(meetingDTO.getRoomId())) {
            throw new MeetingNotFoundException();
        }

        meeting.setFinishedAt(Timestamp.valueOf(LocalDateTime.now()));
        meeting.setMeetingName(meetingDTO.getMeetingName());
        meeting.setRoom(roomId);
        return meetingConverter.toDTO(meetingRepository.save(meeting));
    }

    @Override
    public void joinMeeting(String meeting_id, SignalMessage signalMessage, String sessionId) {
        try {

            boolean isMeetingExists =  MeetingStorage.getInstance().getMeetings().containsKey(meeting_id);
            if (!isMeetingExists) {
                MeetingStorage.getInstance().setMeetings(meeting_id);
            }

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

    @Override
    public List<MeetingDTO> getMeetingByRoomId(Integer roomId) {
        return meetingRepository.findByRoomId(roomId).stream()
                .map(meeting -> meetingConverter.toDTO(meeting)).collect(Collectors.toList());
    }

    public void addPeerTo(String meeting_id, String sessionId, String sender) {
        SignalMessage messagePeerSelf = new SignalMessage();
        messagePeerSelf.setShouldCreateOffer(true);
        messagePeerSelf.setType(SignalMessage.MessageType.ADD_PEER);

        SignalMessage messagePeerOther = new SignalMessage();
        messagePeerOther.setPeerId(sender);
        messagePeerOther.setShouldCreateOffer(false);
        messagePeerOther.setType(SignalMessage.MessageType.ADD_PEER);
        messagePeerOther.setPeerName(getFullNameByUserId(Integer.parseInt(sender)));

        logger.info(MeetingStorage.getInstance().getMeetings().get(meeting_id).toString());

        MeetingStorage.getInstance().getMeetings().get(meeting_id).forEach(id -> {
            simpMessagingTemplate.convertAndSendToUser(id, "/topic/messages/" + meeting_id,
                    messagePeerOther);

            messagePeerSelf.setPeerId(id);
            messagePeerSelf.setPeerName(getFullNameByUserId(Integer.parseInt(id)));
            simpMessagingTemplate.convertAndSendToUser(sender,"/topic/messages/" + meeting_id,
                    messagePeerSelf);
        });
    }

    @Override
    public void relayICECandidate(String meeting_id, SignalMessage signalMessage, String sessionId) {
//            logger.debug(String.format("[%s] replay ICE candidate to %s : %s"), sessionId, peerId, iceCandidate.toString());
        sendToPeer(meeting_id, SignalMessage.MessageType.ICE_CANDIDATE, signalMessage, sessionId);
    }

    @Override
    public void relaySessionDescription(String meeting_id, SignalMessage signalMessage, String sessionId) {
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

    @Override
    public void removePeer(String meeting_id, SignalMessage signalMessage, String sessionId) {
        if (!MeetingStorage.getInstance().getMeetings().containsKey(meeting_id)) {
            logger.debug(String.format("[%] [Warning] not in %s", sessionId, meeting_id));
            return;
        }

        SignalMessage messagePeerSelf = new SignalMessage();
        messagePeerSelf.setType(SignalMessage.MessageType.REMOVE_PEER);

        SignalMessage messagePeerOther = new SignalMessage();
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

    @Override
    public MeetingDTO findByMeetingId(UUID meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new MeetingNotFoundException());
        return meetingConverter.toDTO(meeting);
    }

    @Override
    public boolean existMeetingByMeetingId(UUID meetingId) {
        return meetingRepository.findById(meetingId).isPresent();
    }

    public String getFullNameByUserId(Integer userId) {
        User user = userRepository.findFirstByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException());
        return user.getFullName();
    }
}
