package com.wbsedcl.trms.feeder.management.domain.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FeederTextValidator implements ConstraintValidator<ValidFeederText, String> {

    @Override
    public void initialize(ValidFeederText constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String feederTextToValidate, ConstraintValidatorContext constraintValidatorContext) {
        if (feederTextToValidate == null || feederTextToValidate.trim().equals("")){
            return false;
        }
        return true;
    }
}
