package com.videoconference.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
public class Room {
    private Integer roomId;
    private String roomName;
    private String roomCode;
    private Integer avatarId;
    private Boolean isPublic;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer createdBy;
    private Collection<Documents> documentsByRoomId;
    private Collection<ParticipantRoom> participantRoomsByRoomId;
    private Image imageByAvatarId;

    @Id
    @Column(name = "room_id")
    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    @Column(name = "room_name")
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Column(name = "room_code")
    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    @Column(name = "avatar_id")
    public Integer getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
    }

    @Column(name = "is_public")
    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    @Column(name = "created_at")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "updated_at")
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
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

        Room room = (Room) o;

        if (roomId != null ? !roomId.equals(room.roomId) : room.roomId != null) return false;
        if (roomName != null ? !roomName.equals(room.roomName) : room.roomName != null) return false;
        if (roomCode != null ? !roomCode.equals(room.roomCode) : room.roomCode != null) return false;
        if (avatarId != null ? !avatarId.equals(room.avatarId) : room.avatarId != null) return false;
        if (isPublic != null ? !isPublic.equals(room.isPublic) : room.isPublic != null) return false;
        if (createdAt != null ? !createdAt.equals(room.createdAt) : room.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(room.updatedAt) : room.updatedAt != null) return false;
        if (createdBy != null ? !createdBy.equals(room.createdBy) : room.createdBy != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roomId != null ? roomId.hashCode() : 0;
        result = 31 * result + (roomName != null ? roomName.hashCode() : 0);
        result = 31 * result + (roomCode != null ? roomCode.hashCode() : 0);
        result = 31 * result + (avatarId != null ? avatarId.hashCode() : 0);
        result = 31 * result + (isPublic != null ? isPublic.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "roomByRoomId")
    public Collection<Documents> getDocumentsByRoomId() {
        return documentsByRoomId;
    }

    public void setDocumentsByRoomId(Collection<Documents> documentsByRoomId) {
        this.documentsByRoomId = documentsByRoomId;
    }

    @OneToMany(mappedBy = "roomByRoomId")
    public Collection<ParticipantRoom> getParticipantRoomsByRoomId() {
        return participantRoomsByRoomId;
    }

    public void setParticipantRoomsByRoomId(Collection<ParticipantRoom> participantRoomsByRoomId) {
        this.participantRoomsByRoomId = participantRoomsByRoomId;
    }

    @ManyToOne
    @JoinColumn(name = "avatar_id", referencedColumnName = "image_id")
    public Image getImageByAvatarId() {
        return imageByAvatarId;
    }

    public void setImageByAvatarId(Image imageByAvatarId) {
        this.imageByAvatarId = imageByAvatarId;
    }
}
