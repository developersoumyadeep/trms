package com.wbsedcl.trms.substation.log.domain.validation;

import com.wbsedcl.trms.substation.log.domain.entity.Feeder;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.FeederRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
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
