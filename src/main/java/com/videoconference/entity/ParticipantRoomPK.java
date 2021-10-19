package com.videoconference.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ParticipantRoomPK implements Serializable {
    private Integer roomId;
    private Integer participantId;

    @Column(name = "room_id")
    @Id
    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
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

        ParticipantRoomPK that = (ParticipantRoomPK) o;

        if (roomId != null ? !roomId.equals(that.roomId) : that.roomId != null) return false;
        if (participantId != null ? !participantId.equals(that.participantId) : that.participantId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roomId != null ? roomId.hashCode() : 0;
        result = 31 * result + (participantId != null ? participantId.hashCode() : 0);
        return result;
    }
}
