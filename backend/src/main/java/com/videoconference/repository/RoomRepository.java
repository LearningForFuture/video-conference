package com.videoconference.repository;

import com.videoconference.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("SELECT r FROM Room r WHERE r.roomId = :roomId")
    Optional<Room> getRoomByRoomId(Integer roomId);
    @Query("SELECT r FROM Room r WHERE r.roomCode = :roomCode")
    Optional<Room> getRoomByRoomCode(String roomCode);

    @Query("SELECT r.roomId FROM Room r WHERE r.roomCode = :roomCode")
    Optional<Integer> getRoomIdByRoomCode(String roomCode);
}
