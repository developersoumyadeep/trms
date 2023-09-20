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
@Constraint(validatedBy = FeederTextValidator.class)
public @interface ValidFeederText {
    String message() default "Feeder text is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
