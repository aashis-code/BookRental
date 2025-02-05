package com.bookrental.validation.date;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface  ValidDate {

    String message() default "Invalid date.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
