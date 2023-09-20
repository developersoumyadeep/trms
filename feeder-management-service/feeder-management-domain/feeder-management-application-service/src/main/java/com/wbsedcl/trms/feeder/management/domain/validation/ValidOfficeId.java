package com.wbsedcl.trms.feeder.management.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ConstraintComposition(CompositionType.ALL_FALSE)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OfficeIdValidator.class)
public @interface ValidOfficeId {
    String message() default "Office id is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
