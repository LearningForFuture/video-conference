package com.videoconference.service;

import com.videoconference.dto.users.RoomDTO;
import com.videoconference.entity.Room;

public interface RoomService {
    RoomDTO getRoomByRoomId(Integer roomId);

    void addUser(Integer roomId, String[] emails);

    Room getRoomByRoomCode(String roomCode);

    boolean isExistRoom(String roomCode);

    void joinRoomByRoomCode(String roomCode, String username);

    void removeUser(Integer roomId, Integer userId);
}
