package com.videoconference.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "participant_meeting", schema = "videoconference", catalog = "")
@IdClass(ParticipantMeetingPK.class)
public class ParticipantMeeting {
    private Integer meetingId;
    private Integer participantId;
    private Timestamp joinedAt;
    private Timestamp leftAt;
    private Meeting meetingByMeetingId;
    private Users usersByParticipantId;

    @Id
    @Column(name = "meeting_id")
    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    @Id
    @Column(name = "participant_id")
    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    @Column(name = "joined_at")
    public Timestamp getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Timestamp joinedAt) {
        this.joinedAt = joinedAt;
    }

    @Column(name = "left_at")
    public Timestamp getLeftAt() {
        return leftAt;
    }

    public void setLeftAt(Timestamp leftAt) {
        this.leftAt = leftAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParticipantMeeting that = (ParticipantMeeting) o;

        if (meetingId != null ? !meetingId.equals(that.meetingId) : that.meetingId != null) return false;
        if (participantId != null ? !participantId.equals(that.participantId) : that.participantId != null)
            return false;
        if (joinedAt != null ? !joinedAt.equals(that.joinedAt) : that.joinedAt != null) return false;
        if (leftAt != null ? !leftAt.equals(that.leftAt) : that.leftAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = meetingId != null ? meetingId.hashCode() : 0;
        result = 31 * result + (participantId != null ? participantId.hashCode() : 0);
        result = 31 * result + (joinedAt != null ? joinedAt.hashCode() : 0);
        result = 31 * result + (leftAt != null ? leftAt.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "meeting_id", referencedColumnName = "meeting_id", nullable = false)
    public Meeting getMeetingByMeetingId() {
        return meetingByMeetingId;
    }

    public void setMeetingByMeetingId(Meeting meetingByMeetingId) {
        this.meetingByMeetingId = meetingByMeetingId;
    }

    @ManyToOne
    @JoinColumn(name = "participant_id", referencedColumnName = "user_id", nullable = false)
    public Users getUsersByParticipantId() {
        return usersByParticipantId;
    }

    public void setUsersByParticipantId(Users usersByParticipantId) {
        this.usersByParticipantId = usersByParticipantId;
    }
}
