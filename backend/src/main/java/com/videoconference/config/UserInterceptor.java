package com.videoconference.config;

import com.videoconference.security.User;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);

            if (raw instanceof Map) {
                Object name = ((Map) raw).get("sender");
                Object meetingId = ((Map)raw).get("meetingId");
                if (meetingId instanceof ArrayList && meetingId != null) {
                    accessor.getSessionAttributes().put("meeting_id", ((ArrayList<String>) meetingId).get(0).toString());
                }

                if (name instanceof ArrayList) {
                    accessor.setUser(new User(((ArrayList<String>) name).get(0).toString()));
                }
            }
        }
        return message;
    }
}
