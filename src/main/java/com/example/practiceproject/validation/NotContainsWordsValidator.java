package com.example.practiceproject.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class NotContainsWordsValidator implements ConstraintValidator<NotContainsWords, String> {

    private static final List<String> FORBIDDEN_WORDS = List.of(
            "блят", "бляд", "сука", "сучк", "хуй", "хуе", "хуя", "хули",
            "пизд", "ебан", "ебат", "ёбан", "пидор", "мудак", "залуп", "гондон"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }

        String lowerCaseValue = value.toLowerCase();

        for (String word : FORBIDDEN_WORDS) {
            if (lowerCaseValue.contains(word)) {
                return false;
            }
        }
        return true;
    }
}
