package com.videoconference.validator;

import com.videoconference.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistUsernameValidator implements ConstraintValidator<ExistUsername, String> {
    @Autowired
    private UserService userService;

    @Override
    public void initialize(ExistUsername constraintAnnotation) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        // ignore check exist email
        if(username.isEmpty() || "".equals(username)) return true;
        return !userService.isExistUsername(username);
    }
}
