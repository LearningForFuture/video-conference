package com.videoconference.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ICECandidate {
    private Long sdpMLineIndex;
    private String candidate;
}
