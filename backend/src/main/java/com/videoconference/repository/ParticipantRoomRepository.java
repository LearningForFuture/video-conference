package com.videoconference.repository;

import com.videoconference.entity.ParticipantRoom;
import com.videoconference.entity.ParticipantRoomPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRoomRepository extends JpaRepository<ParticipantRoom, ParticipantRoomPK> {
    @Query("select pr from ParticipantRoom pr where pr.room.roomId = :roomId")
    List<ParticipantRoom> findByRoomId(@Param("roomId") Integer roomId);

    @Query("select pr from ParticipantRoom pr where pr.participant.userId = :participant_id and pr.room.roomId = :roomId")
    Optional<ParticipantRoom> findByParticipantIdAndRoomId(@Param("participant_id") Integer userId,
                                                           @Param("roomId") Integer roomId);
}
