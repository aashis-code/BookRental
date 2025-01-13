package com.bookrental.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (value != null && !value.isEmpty() && value.matches("^[a-zA-Z0-9!@#$%_-]{4,30}@[a-zA-z]{4,8}\\.{2,8}$"));
    }
}
