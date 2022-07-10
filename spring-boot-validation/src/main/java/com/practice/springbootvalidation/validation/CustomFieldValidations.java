package com.practice.springbootvalidation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to group multiple {@code @HasOnlyOnevalue} annotations to validate.
 * <pre>
 * Example: {@code
 * @Conditionals({
 *         @HasOnlyOneValue(fieldNames = {"field1", "field2"}, message = "message to be shown when validation fails"),
 *         @HasOnlyOneValue(fieldNames = {"field3","field4", "field5"}, message = "message to be shown when validation fails")
 * })}
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CustomFieldValidations {
    CustomFieldValidation[] value();
}
