package com.example.practiceproject.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotContainsWordsValidator.class)
public @interface NotContainsWords {
    String message() default "text contains forbidden words";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
