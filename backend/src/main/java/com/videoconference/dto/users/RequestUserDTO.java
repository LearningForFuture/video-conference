package com.videoconference.dto.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class RequestUserDTO {
    private String fullName;
    private String email;
    private String username;
    private String password;
    private Set<Integer> roleId;
}
