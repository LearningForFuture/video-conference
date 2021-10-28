package com.videoconference.repository;

import com.videoconference.entity.ParticipantRoom;
import com.videoconference.entity.ParticipantRoomPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRoomRepository extends JpaRepository<ParticipantRoom, ParticipantRoomPK> {
}
