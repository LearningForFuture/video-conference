package com.videoconference.controller;

import com.videoconference.dto.users.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleException(MethodArgumentNotValidException methodEx, WebRequest request) {
//        logger.error(ex.getClass().getName() + ": " + ex.getMessage());
        List<ApiError> errors = new ArrayList<>();
        methodEx.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = error instanceof FieldError
                    ? ((FieldError) error).getField()
                    : error.getObjectName();
            String errorMessage = error.getDefaultMessage();
            errors.add(new ApiError().setSource(fieldName).setMessage(errorMessage));
        });
        return new ResponseEntity(mapErrorsException(errors, request.getContextPath()), HttpStatus.BAD_REQUEST);
    }

    @MessageExceptionHandler(org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException.class)
    public void handleWebSocketException(org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException methodEx,
                                         @DestinationVariable String meeting_id,
                                         SimpMessageHeaderAccessor headerAccessor) {

        String userId = headerAccessor.getUser().getName();
        List<ApiError> errors = new ArrayList<>();
        methodEx.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = error instanceof FieldError
                    ? ((FieldError) error).getField()
                    : error.getObjectName();
            String errorMessage = error.getDefaultMessage();
            errors.add(new ApiError().setSource(fieldName).setMessage(errorMessage));
        });
        simpMessagingTemplate.convertAndSendToUser(userId, "/topic/messages/" + meeting_id,
                mapErrorsException(errors, null));
        simpMessagingTemplate.convertAndSendToUser(userId, "/topic/meeting/" + meeting_id + "/chat",
                mapErrorsException(errors, null));
    }

    public Map<String, Object> mapErrorsException(List<ApiError> errors, String path) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 400);
        body.put("path", path);
        body.put("errors", errors);
        return body;
    }
}
