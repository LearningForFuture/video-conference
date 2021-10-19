package com.videoconference.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
public class Users {
    private Integer userId;
    private String fullName;
    private String email;
    private String password;
    private Integer avatarId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Collection<Documents> documentsByUserId;
    private Collection<Documents> documentsByUserId_0;
    private Collection<Messages> messagesByUserId;
    private Collection<ParticipantMeeting> participantMeetingsByUserId;
    private Collection<ParticipantRoom> participantRoomsByUserId;
    private Image imageByAvatarId;
    private Collection<UsersRole> usersRolesByUserId;

    @Id
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "avatar_id")
    public Integer getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Users users = (Users) o;

        if (userId != null ? !userId.equals(users.userId) : users.userId != null) return false;
        if (fullName != null ? !fullName.equals(users.fullName) : users.fullName != null) return false;
        if (email != null ? !email.equals(users.email) : users.email != null) return false;
        if (password != null ? !password.equals(users.password) : users.password != null) return false;
        if (avatarId != null ? !avatarId.equals(users.avatarId) : users.avatarId != null) return false;
        if (createdAt != null ? !createdAt.equals(users.createdAt) : users.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(users.updatedAt) : users.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (avatarId != null ? avatarId.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "usersByCreatedBy")
    public Collection<Documents> getDocumentsByUserId() {
        return documentsByUserId;
    }

    public void setDocumentsByUserId(Collection<Documents> documentsByUserId) {
        this.documentsByUserId = documentsByUserId;
    }

    @OneToMany(mappedBy = "usersByModifiedBy")
    public Collection<Documents> getDocumentsByUserId_0() {
        return documentsByUserId_0;
    }

    public void setDocumentsByUserId_0(Collection<Documents> documentsByUserId_0) {
        this.documentsByUserId_0 = documentsByUserId_0;
    }

    @OneToMany(mappedBy = "usersBySenderId")
    public Collection<Messages> getMessagesByUserId() {
        return messagesByUserId;
    }

    public void setMessagesByUserId(Collection<Messages> messagesByUserId) {
        this.messagesByUserId = messagesByUserId;
    }

    @OneToMany(mappedBy = "usersByParticipantId")
    public Collection<ParticipantMeeting> getParticipantMeetingsByUserId() {
        return participantMeetingsByUserId;
    }

    public void setParticipantMeetingsByUserId(Collection<ParticipantMeeting> participantMeetingsByUserId) {
        this.participantMeetingsByUserId = participantMeetingsByUserId;
    }

    @OneToMany(mappedBy = "usersByParticipantId")
    public Collection<ParticipantRoom> getParticipantRoomsByUserId() {
        return participantRoomsByUserId;
    }

    public void setParticipantRoomsByUserId(Collection<ParticipantRoom> participantRoomsByUserId) {
        this.participantRoomsByUserId = participantRoomsByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "avatar_id", referencedColumnName = "image_id")
    public Image getImageByAvatarId() {
        return imageByAvatarId;
    }

    public void setImageByAvatarId(Image imageByAvatarId) {
        this.imageByAvatarId = imageByAvatarId;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<UsersRole> getUsersRolesByUserId() {
        return usersRolesByUserId;
    }

    public void setUsersRolesByUserId(Collection<UsersRole> usersRolesByUserId) {
        this.usersRolesByUserId = usersRolesByUserId;
    }
}
