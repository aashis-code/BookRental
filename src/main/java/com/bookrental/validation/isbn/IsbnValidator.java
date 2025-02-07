package com.bookrental.validation.isbn;

import com.bookrental.validation.email.ValidEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsbnValidator implements ConstraintValidator<ValidIsbn, String> {

    private static final String ISBN_REGEX = "^\\d{13}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null) {
            return value.matches(ISBN_REGEX);
        }
        return true;
    }
}
