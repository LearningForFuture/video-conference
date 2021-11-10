package com.videoconference.dto.users;

import com.videoconference.validator.PasswordMatches;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@PasswordMatches
@Data
public class PasswordResetDTO implements Serializable {
    @NotNull
    @NotEmpty(message = "Không được bỏ trống pasword")
    private String password;
    @NotNull
    @NotEmpty(message = "Không được bỏ trống confirmPassword")
    private String confirmPassword;
}
