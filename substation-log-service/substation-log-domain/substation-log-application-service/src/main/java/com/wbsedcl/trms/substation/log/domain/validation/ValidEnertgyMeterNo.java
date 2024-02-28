package com.wbsedcl.trms.substation.log.domain.validation;


import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ConstraintComposition(CompositionType.ALL_FALSE)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnergyMeterValidator.class)
public @interface ValidEnertgyMeterNo {
    String message() default "Energy meter serial number is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
