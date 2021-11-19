package com.videoconference.service.impl;

import com.videoconference.converter.RoomConverter;
import com.videoconference.dto.room.RoomDTO;
import com.videoconference.entity.ParticipantRoom;
import com.videoconference.entity.ParticipantRoomPK;
import com.videoconference.entity.Room;
import com.videoconference.entity.User;
import com.videoconference.exception.RoomNotFoundException;
import com.videoconference.exception.UserNotFoundException;
import com.videoconference.repository.ParticipantRoomRepository;
import com.videoconference.repository.RoomRepository;
import com.videoconference.repository.UserRepository;
import com.videoconference.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipantRoomRepository participantRoomRepository;

    @Autowired
    private RoomConverter roomConverter;

    @Override
    public boolean isExistRoomName(String roomName) {
        return roomRepository.findByRoomName(roomName).isPresent();
    }

    @Override
    public boolean isExistRoomId(Integer roomId) {
        return roomRepository.findById(roomId).isPresent();
    }

    @Override
    public Room createRoom(RoomDTO roomDTO) {
        User creator = userRepository.findFirstByUserId(roomDTO.getCreatedBy())
                .orElseThrow(() -> new UserNotFoundException());
        // create new participant room and add creator of a room into the room
        Room room = roomConverter.toEntity(roomDTO);
        room.setRoomCode(generateRoomCode());
        room.setIsAdmin(true);
        room.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        room.setCreatedBy(creator);

        ParticipantRoom participantRoom = new ParticipantRoom();
        participantRoom.setParticipant(creator);
        participantRoom.setRoom(room);
        participantRoom.setJoinedAt(Timestamp.valueOf(LocalDateTime.now()));

        room.getParticipantRooms().add(participantRoom);
        return roomRepository.save(room);
    }

    public String generateRoomCode(){
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> findByCreateBy(Integer createBy) {
        User user = userRepository.findFirstByUserId(createBy)
                .orElseThrow(() -> new UserNotFoundException());
        return new ArrayList<>(user.getRooms());
    }

    @Override
    public Room findByRoomIdAndUserId(Integer roomId, Integer userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException());
        participantRoomRepository.findByParticipantIdAndRoomId(userId, roomId)
                .orElseThrow(() -> new RoomNotFoundException());
        return room;
    }
}
