package com.videoconference.repository;

import com.videoconference.entity.ParticipantMeeting;
import com.videoconference.entity.ParticipantMeetingPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantMeetingRepository extends JpaRepository<ParticipantMeeting, ParticipantMeetingPK> {
}
