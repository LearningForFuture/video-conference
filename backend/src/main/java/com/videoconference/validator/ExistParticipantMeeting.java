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
@Constraint(validatedBy = ExistParticipantMeetingValidator.class)
@Documented
public @interface ExistParticipantMeeting {
    String message() default "Your access has been denied.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
