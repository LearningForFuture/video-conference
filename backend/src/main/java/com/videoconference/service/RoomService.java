package com.videoconference.service;

import com.videoconference.dto.room.RoomDTO;
import com.videoconference.entity.Room;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoomService {
    boolean isExistRoomName(String roomName);

    boolean isExistRoomId(Integer roomId);

    Room createRoom(RoomDTO roomDTO);

    List<Room> findAll();

    List<Room> findByCreateBy(Integer createBy);

    Room findByRoomIdAndUserId(Integer roomId, Integer userId);

    RoomDTO getRoomByRoomId(Integer roomId);

    void addUser(Integer roomId, String[] emails);

    Room getRoomByRoomCode(String roomCode);

    boolean isExistRoom(String roomCode);

    RoomDTO joinRoomByRoomCode(String roomCode, String username);

    void removeUser(Integer roomId, Integer userId);

    Page<RoomDTO> getRooms(int page, int size, String[] sort, String keyword);

    List<Room> findRoomByParticipantId(Integer participantId);
}
