package com.videoconference.converter;

import com.videoconference.dto.room.RoomDTO;
import com.videoconference.entity.Room;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RoomConverter {
    public Room toEntity(RoomDTO roomDTO) {
        Room room = new Room();
        room.setRoomName(roomDTO.getRoomName());
        room.setIsPublic(roomDTO.getIsPublic());
        room.setCreatedAt(roomDTO.getCreatedAt());
        room.setUpdatedAt(roomDTO.getUpdatedAt());
        return room;
    }

    public RoomDTO toDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomId(room.getRoomId());
        roomDTO.setRoomName(room.getRoomName());
        roomDTO.setIsPublic(room.getIsPublic());
        roomDTO.setCreatedAt(room.getCreatedAt());
        roomDTO.setUpdatedAt(room.getUpdatedAt());
        roomDTO.setCreatedBy(room.getCreatedByUser().getUserId());
        roomDTO.setIsAdmin(room.getIsAdmin());
//        roomDTO.setParticipantRooms(new ArrayList<>(room.getParticipantRooms()));
        return roomDTO;
    }
}
