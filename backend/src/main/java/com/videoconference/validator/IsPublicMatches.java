package com.videoconference.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = IsPublicMatchesValidator.class)
public @interface IsPublicMatches {
    String message() default "Loaị truy cập được cho phép true or false";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
