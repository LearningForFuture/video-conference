package com.videoconference.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = RoomCodeValidator.class)
@Documented
public @interface ExistRoomCode {
    String message() default "Room code isn't found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
