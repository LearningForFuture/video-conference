package com.videoconference.dto.room;

import com.videoconference.validator.ExistRoomCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class JoinByRoomCodeDTO {
    @NotNull
    @NotEmpty(message = "Không được bỏ trống mã phòng họp")
    @ExistRoomCode
    private String roomCode;
}
