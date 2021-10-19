package com.videoconference.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Documents {
    private Integer documentId;
    private Integer roomId;
    private String nameFile;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Integer createdBy;
    private Integer modifiedBy;
    private Room roomByRoomId;
    private Users usersByCreatedBy;
    private Users usersByModifiedBy;

    @Id
    @Column(name = "document_id")
    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    @Column(name = "room_id")
    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    @Column(name = "name_file")
    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    @Column(name = "created_at")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "modified_at")
    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Column(name = "created_by")
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "modified_by")
    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Documents documents = (Documents) o;

        if (documentId != null ? !documentId.equals(documents.documentId) : documents.documentId != null) return false;
        if (roomId != null ? !roomId.equals(documents.roomId) : documents.roomId != null) return false;
        if (nameFile != null ? !nameFile.equals(documents.nameFile) : documents.nameFile != null) return false;
        if (createdAt != null ? !createdAt.equals(documents.createdAt) : documents.createdAt != null) return false;
        if (modifiedAt != null ? !modifiedAt.equals(documents.modifiedAt) : documents.modifiedAt != null) return false;
        if (createdBy != null ? !createdBy.equals(documents.createdBy) : documents.createdBy != null) return false;
        if (modifiedBy != null ? !modifiedBy.equals(documents.modifiedBy) : documents.modifiedBy != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = documentId != null ? documentId.hashCode() : 0;
        result = 31 * result + (roomId != null ? roomId.hashCode() : 0);
        result = 31 * result + (nameFile != null ? nameFile.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (modifiedAt != null ? modifiedAt.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (modifiedBy != null ? modifiedBy.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
    public Room getRoomByRoomId() {
        return roomByRoomId;
    }

    public void setRoomByRoomId(Room roomByRoomId) {
        this.roomByRoomId = roomByRoomId;
    }

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    public Users getUsersByCreatedBy() {
        return usersByCreatedBy;
    }

    public void setUsersByCreatedBy(Users usersByCreatedBy) {
        this.usersByCreatedBy = usersByCreatedBy;
    }

    @ManyToOne
    @JoinColumn(name = "modified_by", referencedColumnName = "user_id")
    public Users getUsersByModifiedBy() {
        return usersByModifiedBy;
    }

    public void setUsersByModifiedBy(Users usersByModifiedBy) {
        this.usersByModifiedBy = usersByModifiedBy;
    }
}
