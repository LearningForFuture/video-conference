package com.videoconference.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ParticipantMeetingPK implements Serializable {
    private Integer meetingId;
    private Integer participantId;

    @Column(name = "meeting_id")
    @Id
    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    @Column(name = "participant_id")
    @Id
    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParticipantMeetingPK that = (ParticipantMeetingPK) o;

        if (meetingId != null ? !meetingId.equals(that.meetingId) : that.meetingId != null) return false;
        if (participantId != null ? !participantId.equals(that.participantId) : that.participantId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = meetingId != null ? meetingId.hashCode() : 0;
        result = 31 * result + (participantId != null ? participantId.hashCode() : 0);
        return result;
    }
}
