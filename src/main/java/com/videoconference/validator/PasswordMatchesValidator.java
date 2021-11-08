package com.videoconference.validator;

import com.videoconference.dto.users.CreateUserDTO;
import com.videoconference.dto.users.PasswordResetDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        String password = "";
        String confirmPassword = "";

        if (obj instanceof CreateUserDTO) {
            password = ((CreateUserDTO) obj).getPassword();
            confirmPassword = ((CreateUserDTO) obj).getConfirmPassword();
        } else if (obj instanceof PasswordResetDTO) {
            password = ((PasswordResetDTO) obj).getPassword();
            confirmPassword = ((PasswordResetDTO) obj).getConfirmPassword();
        }
        return password.equals(confirmPassword);
    }
}