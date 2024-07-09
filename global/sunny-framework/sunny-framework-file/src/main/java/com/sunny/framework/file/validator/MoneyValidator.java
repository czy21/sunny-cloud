package com.sunny.framework.file.validator;


import com.sunny.framework.file.constraint.IsMoney;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class MoneyValidator implements ConstraintValidator<IsMoney, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        BigDecimal decimal;
        try {
            decimal = new BigDecimal(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
