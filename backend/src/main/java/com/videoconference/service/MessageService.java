package com.videoconference.service;

import com.videoconference.dto.message.MessageDTO;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    MessageDTO addMessage(MessageDTO messageDTO);
    List<MessageDTO> findMessageByMeetingId(UUID meetingId);
}
