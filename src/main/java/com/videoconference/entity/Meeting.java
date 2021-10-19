package com.videoconference.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
public class Meeting {
    private Integer meetingId;
    private Integer roomId;
    private String meetingName;
    private Timestamp startedAt;
    private Timestamp finishedAt;
    private Integer createdBy;
    private Collection<Messages> messagesByMeetingId;
    private Collection<ParticipantMeeting> participantMeetingsByMeetingId;

    @Id
    @Column(name = "meeting_id")
    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    @Column(name = "room_id")
    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    @Column(name = "meeting_name")
    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    @Column(name = "started_at")
    public Timestamp getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Timestamp startedAt) {
        this.startedAt = startedAt;
    }

    @Column(name = "finished_at")
    public Timestamp getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Timestamp finishedAt) {
        this.finishedAt = finishedAt;
    }

    @Column(name = "created_by")
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meeting meeting = (Meeting) o;

        if (meetingId != null ? !meetingId.equals(meeting.meetingId) : meeting.meetingId != null) return false;
        if (roomId != null ? !roomId.equals(meeting.roomId) : meeting.roomId != null) return false;
        if (meetingName != null ? !meetingName.equals(meeting.meetingName) : meeting.meetingName != null) return false;
        if (startedAt != null ? !startedAt.equals(meeting.startedAt) : meeting.startedAt != null) return false;
        if (finishedAt != null ? !finishedAt.equals(meeting.finishedAt) : meeting.finishedAt != null) return false;
        if (createdBy != null ? !createdBy.equals(meeting.createdBy) : meeting.createdBy != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = meetingId != null ? meetingId.hashCode() : 0;
        result = 31 * result + (roomId != null ? roomId.hashCode() : 0);
        result = 31 * result + (meetingName != null ? meetingName.hashCode() : 0);
        result = 31 * result + (startedAt != null ? startedAt.hashCode() : 0);
        result = 31 * result + (finishedAt != null ? finishedAt.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "meetingByMeetingId")
    public Collection<Messages> getMessagesByMeetingId() {
        return messagesByMeetingId;
    }

    public void setMessagesByMeetingId(Collection<Messages> messagesByMeetingId) {
        this.messagesByMeetingId = messagesByMeetingId;
    }

    @OneToMany(mappedBy = "meetingByMeetingId")
    public Collection<ParticipantMeeting> getParticipantMeetingsByMeetingId() {
        return participantMeetingsByMeetingId;
    }

    public void setParticipantMeetingsByMeetingId(Collection<ParticipantMeeting> participantMeetingsByMeetingId) {
        this.participantMeetingsByMeetingId = participantMeetingsByMeetingId;
    }
}
