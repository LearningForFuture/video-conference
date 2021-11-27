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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String fullName;
    private String email;
    private String username;
    @Column(length = 255)
    private String password;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean enabled = false;

    @OneToMany(mappedBy = "userByCreatedBy")
    private Set<Document> createBy = new HashSet<>();

    @OneToMany(mappedBy = "userByModifiedBy")
    private Set<Document> modifiedBy = new HashSet<>();

    @OneToMany(mappedBy = "sender")
    private Set<Message> messages = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<ParticipantMeeting> participantMeetings = new HashSet<>();

    @OneToMany(mappedBy = "createdBy")
    private Set<Meeting> meetings = new HashSet<>();

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
    private Set<ParticipantRoom> participantRooms = new HashSet<>();

//    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
//    private Set<Room> rooms = new HashSet<>();

    @OneToMany(mappedBy = "createdByUser")
    private Set<Room> rooms = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_id")
    private Image image;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
