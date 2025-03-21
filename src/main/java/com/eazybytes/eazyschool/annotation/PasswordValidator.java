package com.eazybytes.eazyschool.annotation;

import com.eazybytes.eazyschool.validation.PasswordStrengthValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordStrengthValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValidator {

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String message() default "Entered password is too weak!";



}
