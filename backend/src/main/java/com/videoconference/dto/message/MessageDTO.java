package com.videoconference.dto.message;

import com.videoconference.validator.ExistMeeting;
import com.videoconference.validator.ExistUserId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class MessageDTO {
    private Integer messageId;
    @NotNull
    @NotEmpty(message = "nội dung không được bỏ trống")
    private String body;
    private Timestamp createdAt;
    private Timestamp deletedAt;
    private Boolean isDeleted;
    @ExistUserId
    private Integer senderId;
//    @ExistMeeting
    private UUID meetingId;
    private String fullName;
}
