package com.videoconference.dto.users;

import com.videoconference.validator.*;
import lombok.Data;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@PasswordMatches
@Data
public class CreateUserDTO implements Serializable {
    @NotNull
    @NotEmpty(message = "Không được bỏ trống username")
    @ExistUsername
    private String username;
    @NotNull
    @NotEmpty(message = "Không được bỏ trống email")
    @ValidEmail
    @ExistEmail
    private String email;
    @NotNull
    @NotEmpty(message = "Không được bỏ trống pasword")
    private String password;
    @NotNull
    @NotEmpty(message = "Không được bỏ trống confirmPassword")
    private String confirmPassword;
}
