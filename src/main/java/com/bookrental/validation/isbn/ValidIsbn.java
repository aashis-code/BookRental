package com.bookrental.validation.isbn;

import com.bookrental.validation.email.EmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = IsbnValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIsbn{

    String message() default "Invalid Isbn number.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
