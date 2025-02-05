package com.bookrental.validation.date;

import com.bookrental.exceptions.AppException;
import com.bookrental.helper.constants.MessageConstants;
import com.bookrental.serviceimpl.CustomMessageSource;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class DateValidator implements ConstraintValidator<ValidDate, LocalDate> {

    private final CustomMessageSource customMessageSource;

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if (date != null && !date.isBefore(LocalDate.now())) {
            throw new AppException(customMessageSource.get(MessageConstants.INVALID_DATE));
        }
        return true;
    }
}
