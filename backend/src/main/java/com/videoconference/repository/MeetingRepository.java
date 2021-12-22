package com.videoconference.repository;

import com.videoconference.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, UUID> {
    @Query("SELECT m FROM Meeting m WHERE m.meetingId = :meeting_id AND m.createdBy = :created_by AND m.room.roomId = :room_id")
    Optional<Meeting> findByIdAndCreatedByAndRoomId(@Param("meeting_id") byte[] meetingId,
                                                    @Param("created_by") Integer createdBy,
                                                    @Param("room_id") Integer roomId);

    @Query("SELECT m FROM Meeting  m WHERE m.room.roomId = :roomId")
    List<Meeting> findByRoomId(Integer roomId) ;
}
