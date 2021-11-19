package com.videoconference.dto.room;

import com.videoconference.entity.ParticipantRoom;
import com.videoconference.validator.ExistRoomName;
import com.videoconference.validator.ExistUserId;
import com.videoconference.validator.IsPublicMatches;
import lombok.*;

import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoomDTO {
    private Integer roomId;

    @NotNull
    @NotEmpty(message = "Tên phòng không được bỏ trống")
    @ExistRoomName
    @Size(min = 3, max = 50, message = "Độ dài tên phòng phải từ 3 đến 50 kí tự")
    private String roomName;

    @NotNull
    @IsPublicMatches
    private Boolean isPublic;

//    @NotNull(message = "Người tạo không được bỏ trống")
//    @Positive(message="id người tạo phải lớn hơn 0")
//    @ExistUserId
    private Integer createdBy;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    private Boolean isAdmin;
    private List<ParticipantRoom> participantRooms = new ArrayList<>();
}
