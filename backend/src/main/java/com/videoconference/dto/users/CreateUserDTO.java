package com.videoconference.dto.users;

import com.videoconference.validator.ExistEmail;
import com.videoconference.validator.ExistUsername;
import com.videoconference.validator.PasswordMatches;
import com.videoconference.validator.ValidEmail;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
