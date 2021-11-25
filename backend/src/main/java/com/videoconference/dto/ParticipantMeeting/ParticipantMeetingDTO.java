package com.videoconference.dto.ParticipantMeeting;

import com.videoconference.validator.ExistUserId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ParticipantMeetingDTO {
    private UUID meetingId;
    @ExistUserId
    private Integer participantId;
    private Timestamp joinedAt;
    private Timestamp leftAt;
}
