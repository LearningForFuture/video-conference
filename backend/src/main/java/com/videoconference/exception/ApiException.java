package com.videoconference.exception;

import com.videoconference.dto.users.ApiError;

import java.util.ArrayList;
import java.util.List;

public class ApiException extends RuntimeException{
    private List<ApiError> errors;

    public ApiException() {
        super();
        errors = new ArrayList<>();
    }

    public ApiException(List<ApiError> errors) {
        this.errors = errors;
    }

    public void add(ApiError error) {
        this.errors.add(error);
    }

    public List<ApiError> getErrors() {
        return errors;
    }
}
