package com.videoconference.dto.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ResendRegistration {
    @NotNull
    @NotEmpty(message = "Không được bỏ trống email")
    private String email;
}
