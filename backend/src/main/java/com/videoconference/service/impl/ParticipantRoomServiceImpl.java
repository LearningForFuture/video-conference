package com.videoconference.service.impl;

import com.videoconference.entity.ParticipantRoom;
import com.videoconference.entity.ParticipantRoomPK;
import com.videoconference.entity.User;
import com.videoconference.exception.UserNotFoundException;
import com.videoconference.repository.UserRepository;
import com.videoconference.service.ParticipantRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class ParticipantRoomServiceImpl implements ParticipantRoomService {
    @Autowired
    private UserRepository userRepository;

}
