package com.videoconference.service.impl;

import com.videoconference.converter.MessageConverter;
import com.videoconference.dto.message.MessageDTO;
import com.videoconference.entity.Meeting;
import com.videoconference.entity.Message;
import com.videoconference.entity.User;
import com.videoconference.exception.MeetingNotFoundException;
import com.videoconference.exception.UserNotFoundException;
import com.videoconference.repository.MeetingRepository;
import com.videoconference.repository.MessageRepository;
import com.videoconference.repository.UserRepository;
import com.videoconference.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageConverter messageConverter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Override
    public MessageDTO addMessage(MessageDTO messageDTO) {
        User sender = userRepository.findFirstByUserId(messageDTO.getSenderId())
                .orElseThrow(() -> new UserNotFoundException());
        Meeting meeting = meetingRepository.findById(messageDTO.getMeetingId())
                .orElseThrow(() -> new MeetingNotFoundException());
        Message message = new Message();
        String encodeBody = Base64.getEncoder().encodeToString(messageDTO.getBody().getBytes(StandardCharsets.UTF_8));
        message.setBody(encodeBody);
        message.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        message.setIsDeleted(false);
        message.setSender(sender);
        message.setMeeting(meeting);
        MessageDTO messageResponse = messageConverter.toDTO(messageRepository.save(message));
        byte[] decodeBody = Base64.getDecoder().decode(messageResponse.getBody());
        messageResponse.setBody(new String(decodeBody));
        messageResponse.setFullName(sender.getFullName());
        return messageResponse;
    }

    @Override
    public List<MessageDTO> findMessageByMeetingId(UUID meetingId) {
        List<MessageDTO> messageDTOS = messageRepository.findByMeetingId(meetingId,
                        Sort.by(Sort.Direction.ASC, "createdAt")).stream()
                .map(message -> {
                    User sender = userRepository.findFirstByUserId(message.getSender().getUserId())
                            .orElseThrow(() -> new UserNotFoundException());
                    message.setBody(new String(Base64.getDecoder().decode(message.getBody())));
                    MessageDTO messageDTO = messageConverter.toDTO(message);
                    messageDTO.setFullName(sender.getFullName());
                    return messageDTO;
                }).collect(Collectors.toList());
        return messageDTOS;
    }
}
