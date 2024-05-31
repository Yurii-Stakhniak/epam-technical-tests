package com.evri.interview.validator;

import com.evri.interview.model.FullName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FullNameValidator implements ConstraintValidator<FullName, String> {

    public static final int MIN_WORDS_COUNT_IN_FULL_NAME = 2;

    @Override
    public void initialize(FullName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        String[] names = value.split("\\s+");
        return names.length >= MIN_WORDS_COUNT_IN_FULL_NAME;
    }
}