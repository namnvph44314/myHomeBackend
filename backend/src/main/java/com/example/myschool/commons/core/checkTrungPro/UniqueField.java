package com.example.myschool.commons.core.checkTrungPro;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.internal.constraintvalidators.hv.UniqueElementsValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueElementsValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueField {
    String message() default "Value already exists";

    Class<?> entity();      // entity cần check

    String field();         // field trong entity

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
