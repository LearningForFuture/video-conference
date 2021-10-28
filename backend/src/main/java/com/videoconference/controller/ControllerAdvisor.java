package com.videoconference.controller;

import com.videoconference.dto.users.ApiError;
import com.videoconference.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.*;

@ControllerAdvice
public class ControllerAdvisor {
    private static Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleException(MethodArgumentNotValidException methodEx, WebRequest request) {
        List<ApiError> errors = new ArrayList<>();
        methodEx.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = error.getClass().toString();
            String errorMessage = error.getDefaultMessage();
            errors.add(new ApiError().setSource(fieldName).setMessage(errorMessage));
        });


        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 400);
        body.put("path", request.getContextPath());
        body.put("errors", errors);

//        logger.error(ex.getClass().getName() + ": " + ex.getMessage());
        return new ResponseEntity(body, HttpStatus.BAD_REQUEST);
    }
}
