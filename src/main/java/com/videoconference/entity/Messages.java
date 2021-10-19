package com.videoconference.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Messages {
    private Integer messageId;
    private String body;
    private Integer senderId;
    private Integer meetingId;
    private Timestamp createdAt;
    private Timestamp deletedAt;
    private Boolean isDeleted;
    private Users usersBySenderId;
    private Meeting meetingByMeetingId;

    @Id
    @Column(name = "message_id")
    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Column(name = "body")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Column(name = "sender_id")
    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    @Column(name = "meeting_id")
    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    @Column(name = "created_at")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "deleted_at")
    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Column(name = "is_deleted")
    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Messages messages = (Messages) o;

        if (messageId != null ? !messageId.equals(messages.messageId) : messages.messageId != null) return false;
        if (body != null ? !body.equals(messages.body) : messages.body != null) return false;
        if (senderId != null ? !senderId.equals(messages.senderId) : messages.senderId != null) return false;
        if (meetingId != null ? !meetingId.equals(messages.meetingId) : messages.meetingId != null) return false;
        if (createdAt != null ? !createdAt.equals(messages.createdAt) : messages.createdAt != null) return false;
        if (deletedAt != null ? !deletedAt.equals(messages.deletedAt) : messages.deletedAt != null) return false;
        if (isDeleted != null ? !isDeleted.equals(messages.isDeleted) : messages.isDeleted != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = messageId != null ? messageId.hashCode() : 0;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (senderId != null ? senderId.hashCode() : 0);
        result = 31 * result + (meetingId != null ? meetingId.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (deletedAt != null ? deletedAt.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "user_id")
    public Users getUsersBySenderId() {
        return usersBySenderId;
    }

    public void setUsersBySenderId(Users usersBySenderId) {
        this.usersBySenderId = usersBySenderId;
    }

    @ManyToOne
    @JoinColumn(name = "meeting_id", referencedColumnName = "meeting_id")
    public Meeting getMeetingByMeetingId() {
        return meetingByMeetingId;
    }

    public void setMeetingByMeetingId(Meeting meetingByMeetingId) {
        this.meetingByMeetingId = meetingByMeetingId;
    }
}
