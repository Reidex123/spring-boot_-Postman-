package com.spring.exercise;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UCTEmailValidator implements ConstraintValidator<UCTEmail, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext ctx) {
        if (email == null)
            return true;

        return email.endsWith("@uct.ac.za") || email.endsWith("@myuct.ac.za");
    }

}
