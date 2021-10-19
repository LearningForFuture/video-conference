package com.videoconference.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
public class Image {
    private Integer imageId;
    private String name;
    private String url;
    private String type;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer updatedBy;
    private Collection<Room> roomsByImageId;
    private Collection<Users> usersByImageId;

    @Id
    @Column(name = "image_id")
    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Column(name = "updated_by")
    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (imageId != null ? !imageId.equals(image.imageId) : image.imageId != null) return false;
        if (name != null ? !name.equals(image.name) : image.name != null) return false;
        if (url != null ? !url.equals(image.url) : image.url != null) return false;
        if (type != null ? !type.equals(image.type) : image.type != null) return false;
        if (createdAt != null ? !createdAt.equals(image.createdAt) : image.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(image.updatedAt) : image.updatedAt != null) return false;
        if (updatedBy != null ? !updatedBy.equals(image.updatedBy) : image.updatedBy != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = imageId != null ? imageId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (updatedBy != null ? updatedBy.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "imageByAvatarId")
    public Collection<Room> getRoomsByImageId() {
        return roomsByImageId;
    }

    public void setRoomsByImageId(Collection<Room> roomsByImageId) {
        this.roomsByImageId = roomsByImageId;
    }

    @OneToMany(mappedBy = "imageByAvatarId")
    public Collection<Users> getUsersByImageId() {
        return usersByImageId;
    }

    public void setUsersByImageId(Collection<Users> usersByImageId) {
        this.usersByImageId = usersByImageId;
    }
}
