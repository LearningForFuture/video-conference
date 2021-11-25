package com.videoconference.repository;

import com.videoconference.entity.Message;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT msg FROM Message msg WHERE msg.meeting.meetingId = :meetingId")
    List<Message> findByMeetingId(@Param("meetingId") UUID meetingId, Sort sort);
}
