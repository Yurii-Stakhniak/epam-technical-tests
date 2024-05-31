package com.evri.interview.model;

import com.evri.interview.validator.FullNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FullNameValidator.class)
public @interface FullName {
    String message() default "Name must contain both first name and last name";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
