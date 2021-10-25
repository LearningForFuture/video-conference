package com.videoconference.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageId;
    private String name;
    private String url;
    private String type;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer updatedBy;

    @OneToMany(mappedBy = "image")
    private Set<Room> rooms = new HashSet<>();

    @OneToMany(mappedBy = "image")
    private Set<User> users = new HashSet<>();

}
