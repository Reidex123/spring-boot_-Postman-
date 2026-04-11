package com.spring.exercise;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// Custom Constraints
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UCTEmailValidator.class)
public @interface UCTEmail {

    String message() default "Must be a valid UCT email address";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
