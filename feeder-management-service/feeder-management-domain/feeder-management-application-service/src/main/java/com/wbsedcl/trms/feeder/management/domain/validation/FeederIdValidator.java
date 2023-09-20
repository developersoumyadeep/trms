package com.wbsedcl.trms.feeder.management.domain.validation;


import com.wbsedcl.trms.feeder.management.domain.entity.Feeder;
import com.wbsedcl.trms.feeder.management.domain.ports.output.repository.FeederRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class FeederIdValidator implements ConstraintValidator<ValidFeederId, String> {

    private final FeederRepository feederRepository;

    public FeederIdValidator(FeederRepository feederRepository) {
        this.feederRepository = feederRepository;
    }

    @Override
    public void initialize(ValidFeederId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String feederIdToValidate, ConstraintValidatorContext context) {
        if (feederIdToValidate == null || feederIdToValidate.trim().equals("")) {
            return false;
        }
        Optional<Feeder> feeder = feederRepository.findFeeder(feederIdToValidate);
        if(feeder.isEmpty()) {
            log.error("Feeder with id {} does not exist", feederIdToValidate);
            return false;
        }
        return true;
    }
}
