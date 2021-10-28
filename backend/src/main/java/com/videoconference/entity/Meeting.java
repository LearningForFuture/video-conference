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
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer meetingId;
    private Integer roomId;
    private String meetingName;
    private Timestamp startedAt;
    private Timestamp finishedAt;
    private Integer createdBy;

    @OneToMany(mappedBy = "meeting")
    private Set<Message> messages = new HashSet<>();

    @OneToMany(mappedBy = "meeting")
    private Set<ParticipantMeeting> participantMeetings = new HashSet<>();
}
