package com.wbsedcl.trms.feeder.management.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ConstraintComposition(CompositionType.ALL_FALSE)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FeederIdValidator.class)
public @interface ValidFeederId {
    String message() default "Feeder does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
