package com.videoconference.service;

import com.videoconference.dto.room.RoomDTO;
import com.videoconference.entity.Room;

import java.util.List;

public interface RoomService {
    boolean isExistRoomName(String roomName);

    boolean isExistRoomId(Integer roomId);

    Room createRoom(RoomDTO roomDTO);

    List<Room> findAll();

    List<Room> findByCreateBy(Integer createBy);

    Room findByRoomIdAndUserId(Integer roomId, Integer userId);
}
