package com.practice.springbootvalidation.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * This Annotation takes an array of field names and validates such that only one field has value.
 * <pre>
 * Example 1: {@code @HasOnlyOneValue(fieldNames = {"field1", "field2"}, message = "message to be shown when validation fails") }
 * Example 2: {@code @HasOnlyOneValue(fieldNames = {"field3", "field4", "field5"}, message = "message to be shown when validation fails") }
 * </pre>
 */
@Repeatable(CustomFieldValidations.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CustomFieldValidationImpl.class})
public @interface CustomFieldValidation {
    String message() default "Failure in field validation.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] hasOnlyOne() default {};

    String[] allAreNotNull() default {};
}
