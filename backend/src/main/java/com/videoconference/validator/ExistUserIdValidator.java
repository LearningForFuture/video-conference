package com.videoconference.validator;

import com.videoconference.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistUserIdValidator implements ConstraintValidator<ExistUserId, Integer> {
    @Autowired
    private UserService userService;

    @Override
    public void initialize(ExistUserId constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer userId, ConstraintValidatorContext constraintValidatorContext) {
        return userService.isExistUserId(userId);
    }
}
