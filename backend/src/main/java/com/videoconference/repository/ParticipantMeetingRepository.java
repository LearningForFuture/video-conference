package com.videoconference.repository;

import com.videoconference.entity.ParticipantMeeting;
import com.videoconference.entity.ParticipantMeetingPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParticipantMeetingRepository extends JpaRepository<ParticipantMeeting, ParticipantMeetingPK> {
    @Query("SELECT pm FROM ParticipantMeeting pm WHERE pm.id.meetingId = :meeting_id AND pm.id.participantId = :participant_id")
    Optional<ParticipantMeeting> findByMeetingIdAndParticipantId(@Param("meeting_id") UUID meetingId,
                                                                 @Param("participant_id") Integer participantId);
}
