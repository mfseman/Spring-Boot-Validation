package com.practice.springbootvalidation.validation;


import com.practice.springbootvalidation.models.response.CustomErrorDetailException;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * This class implements logic for  {@code @HasOnlyOnevalue}
 * <pre>
 * Result:
 *     boolean true - if validation success
 *     boolean false - if validation fails
 * </pre>
 */
public class CustomFieldValidationImpl implements ConstraintValidator<CustomFieldValidation, Object> {

    private String[] hasOnlyOne;
    private String[] allAreNotNull;

    @Override
    public void initialize(CustomFieldValidation customFieldValidation) {
        hasOnlyOne = customFieldValidation.hasOnlyOne();
        allAreNotNull = customFieldValidation.allAreNotNull();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (!ObjectUtils.isEmpty(hasOnlyOne)) {
            return validateHasOnlyOne(value);
        } else if (!ObjectUtils.isEmpty(allAreNotNull)) {
            return validateAllAreNotNull(value);
        }
        return true;
    }

    private boolean validateAllAreNotNull(Object value) {
        Field classField;
        for (String field : allAreNotNull) {
            classField = ReflectionUtils.findField(value.getClass(), field);
            classField.setAccessible(true);
            try {
                if (!Objects.isNull(classField.get(value))) {
                    return true;
                }
            } catch (IllegalAccessException e) {
                throw new CustomErrorDetailException(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return false;
    }

    private boolean validateHasOnlyOne(Object value) {
        Field classField;
        boolean foundOne = false;
        try {
            for (String field : hasOnlyOne) {
                classField = ReflectionUtils.findField(value.getClass(), field);
                classField.setAccessible(true);
                if (!Objects.isNull(classField.get(value))) {
                    if (foundOne) {
                        return false;
                    }
                    foundOne = true;
                }
            }
        } catch (IllegalAccessException iae) {
            throw new CustomErrorDetailException(iae.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return foundOne;
    }
}