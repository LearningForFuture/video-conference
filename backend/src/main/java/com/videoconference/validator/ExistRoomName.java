package com.videoconference.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ExistRoomNameValidator.class)
public @interface ExistRoomName {
    String message() default "Room name already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
