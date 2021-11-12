package com.videoconference.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomId;
    private String roomName;
    private String roomCode;
    private Boolean isPublic;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdByUser;

    @OneToMany(mappedBy = "room")
    private Set<Document> documents = new HashSet<>();

//    @OneToMany(mappedBy = "room")
//    private Set<ParticipantRoom> participantRooms = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "avatar_id")
    private Image image;

}
