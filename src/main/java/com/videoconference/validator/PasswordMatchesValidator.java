package com.videoconference.validator;

import com.videoconference.dto.users.CreateUserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        CreateUserDTO user = (CreateUserDTO) obj;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}