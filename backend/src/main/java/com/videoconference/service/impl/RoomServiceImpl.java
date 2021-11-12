package com.videoconference.service.impl;

import com.videoconference.dto.users.RoomDTO;
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

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ParticipantRoomRepository participantRoomRepository;

    @Override
    public RoomDTO getRoomByRoomId(Integer roomId) {
        Room room = roomRepository.getRoomByRoomId(roomId)
                .orElseThrow(() -> new RoomNotFoundException());

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomId(room.getRoomId());
        roomDTO.setRoomName(room.getRoomName());
        roomDTO.setRoomCode(room.getRoomCode());
        roomDTO.setCreatedAt(room.getCreatedAt());
        roomDTO.setUpdatedAt(room.getUpdatedAt());
        roomDTO.setIsPublic(room.getIsPublic());
        roomDTO.setAvartarId(room.getImage().getImageId());
        roomDTO.setCreateBy(room.getCreatedByUser().getUserId());
        return roomDTO;
    }

    @Override
    public void addUser(Integer roomId, String[] emails) {
        Room room = roomRepository.getRoomByRoomId(roomId)
                .orElseThrow(() -> new RoomNotFoundException());
        List<User> users = userRepository.getUsersByEmail(emails).get();
        
        List<ParticipantRoom> participantRooms = new ArrayList<>();
        ParticipantRoom participantRoom = null;
        for(User user : users) {
            participantRoom = new ParticipantRoom();
            participantRoom.setId(new ParticipantRoomPK().setRoomId(roomId).setParticipantId(user.getUserId()));
            participantRoom.setJoinedAt(Timestamp.valueOf(LocalDateTime.now()));
            participantRoom.setIsAdmin(false);
            participantRooms.add(participantRoom);
        }
        
        participantRoomRepository.saveAll(participantRooms);
    }

    @Override
    public Room getRoomByRoomCode(String roomCode) {
        Room room = roomRepository.getRoomByRoomCode(roomCode)
                .orElseThrow(() -> new RoomNotFoundException());
        return room;
    }

    @Override
    public boolean isExistRoom(String roomCode) {
        return roomRepository.getRoomByRoomCode(roomCode).isPresent();
    }

    @Override
    public void joinRoomByRoomCode(String roomCode, String username) {
        Integer userId = userRepository.getUserIdByUsername(username)
                .orElseThrow(()->new UserNotFoundException());
        Integer roomId = roomRepository.getRoomIdByRoomCode(roomCode)
                .orElseThrow(() -> new RoomNotFoundException());

        ParticipantRoom participantRoom = new ParticipantRoom();
        participantRoom.setId(new ParticipantRoomPK().setRoomId(roomId).setParticipantId(userId));
        participantRoom.setJoinedAt(Timestamp.valueOf(LocalDateTime.now()));
        participantRoom.setIsAdmin(false);

        participantRoomRepository.save(participantRoom);
    }

    @Override
    public void removeUser(Integer roomId, Integer userId) {
        ParticipantRoom participantRoom = new ParticipantRoom();
        participantRoom.setId(new ParticipantRoomPK().setRoomId(roomId).setParticipantId(userId));
        participantRoom.setJoinedAt(Timestamp.valueOf(LocalDateTime.now()));
        participantRoom.setIsAdmin(false);

        participantRoomRepository.delete(participantRoom);
    }

}
