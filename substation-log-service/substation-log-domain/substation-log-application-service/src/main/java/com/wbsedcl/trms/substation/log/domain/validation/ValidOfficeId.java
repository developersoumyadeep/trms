package com.wbsedcl.trms.substation.log.domain.validation;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@ConstraintComposition(CompositionType.ALL_FALSE)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OfficeIdValidator.class)
public @interface ValidOfficeId {
    String message() default "Office id is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
