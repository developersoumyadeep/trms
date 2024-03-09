package com.wbsedcl.trms.substation.log.domain.validation;

import com.wbsedcl.trms.domain.valueobject.EnergyMeterSerialNumber;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyMeter;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.EnergyMeterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Component
@Slf4j
public class EnergyMeterValidator implements ConstraintValidator<ValidEnertgyMeterNo, String> {

    private final EnergyMeterRepository energyMeterRepository;

    public EnergyMeterValidator(EnergyMeterRepository energyMeterRepository) {
        this.energyMeterRepository = energyMeterRepository;
    }
    @Override
    public void initialize(ValidEnertgyMeterNo constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String energyMeterNumberToValidate, ConstraintValidatorContext constraintValidatorContext) {
        if (energyMeterNumberToValidate == null || energyMeterNumberToValidate.matches("")) {
            return false;
        }
        Optional<EnergyMeter> energyMeter = energyMeterRepository.findEnergyMeterBySerialNumber(energyMeterNumberToValidate);
        if (energyMeter.isEmpty()) {
            log.error("Energy meter with serial {} does not exist", energyMeterNumberToValidate);
            return false;
        }
        return true;
    }
}
