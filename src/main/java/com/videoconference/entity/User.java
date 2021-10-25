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
    private String password;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "userByCreatedBy")
    private final Set<Document> createBy = new HashSet<>();

    @OneToMany(mappedBy = "userByModifiedBy")
    private final Set<Document> modifiedBy = new HashSet<>();

    @OneToMany(mappedBy = "sender")
    private final Set<Message> messages = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private final Set<ParticipantMeeting> participantMeetings = new HashSet<>();

    @OneToMany(mappedBy = "participant")
    private final Set<ParticipantRoom> participantRooms = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "avatar_id")
    private Image image;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private final Set<Role> roles = new HashSet<>();
}
