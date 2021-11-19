package com.videoconference.dto.message;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ICECandidate {
    private Long sdpMLineIndex;
    private String candidate;
}
