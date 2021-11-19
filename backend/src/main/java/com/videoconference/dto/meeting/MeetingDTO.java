package com.videoconference.dto.meeting;

import com.videoconference.entity.ParticipantMeeting;
import com.videoconference.validator.ExistRoomId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class MeetingDTO {
    private UUID meetingId;
//    @NotNull(message = "Roon Id không được bỏ trống")
//    @Positive(message="Room Id phải lớn hơn 0")
//    @ExistRoomId
    private Integer roomId;

    private String meetingName;
    private Timestamp startedAt;
    private Timestamp finishedAt;
    private Integer createdBy;
    private List<ParticipantMeeting> participantMeetings = new ArrayList<>();
}
