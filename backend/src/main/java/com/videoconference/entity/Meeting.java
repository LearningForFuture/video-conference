package com.videoconference.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Meeting {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue( generator = "uuid2" )
    @GenericGenerator( name = "uuid2", strategy = "uuid2" )
    @Column(columnDefinition = "BINARY(16)" )
    private UUID meetingId;
//    private Integer meetingId;
    private String meetingName;
    private Timestamp startedAt;
    private Timestamp finishedAt;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "meeting")
    private Set<Message> messages = new HashSet<>();

    @OneToMany(mappedBy = "meeting")
    private Set<ParticipantMeeting> participantMeetings = new HashSet<>();
}
