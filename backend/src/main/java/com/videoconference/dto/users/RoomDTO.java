package com.videoconference.dto.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class RoomDTO {
    private Integer roomId;
    private String roomName;
    private String roomCode;
    private Boolean isPublic;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer createBy;
    private Integer avartarId;
}
