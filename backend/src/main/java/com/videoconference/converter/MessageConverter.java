package com.videoconference.converter;

import com.videoconference.dto.message.MessageDTO;
import com.videoconference.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageConverter {
    public MessageDTO toDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessageId(message.getMessageId());
        messageDTO.setBody(message.getBody());
        messageDTO.setCreatedAt(message.getCreatedAt());
        messageDTO.setDeletedAt(message.getDeletedAt());
        messageDTO.setIsDeleted(message.getIsDeleted());
        messageDTO.setSenderId(message.getSender().getUserId());
        messageDTO.setMeetingId(message.getMeeting().getMeetingId());
        return messageDTO;
    }
}
