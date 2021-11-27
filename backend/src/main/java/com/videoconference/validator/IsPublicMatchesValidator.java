package com.videoconference.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class IsPublicMatchesValidator implements ConstraintValidator<IsPublicMatches, Boolean> {
    @Override
    public void initialize(IsPublicMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Boolean value, ConstraintValidatorContext constraintValidatorContext) {
        return Pattern.matches("^true$|^false$", value.toString());
    }
}
