package com.wbsedcl.trms.feeder.management.domain.validation;

import com.wbsedcl.trms.feeder.management.domain.entity.Office;
import com.wbsedcl.trms.feeder.management.domain.ports.output.repository.OfficeRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class OfficeIdValidator implements ConstraintValidator<ValidOfficeId, String> {

    private final OfficeRepository officeRepository;

    public OfficeIdValidator(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    @Override
    public void initialize(ValidOfficeId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String officeIdToValidate, ConstraintValidatorContext context) {
        Optional<Office> office = officeRepository.findOffice(officeIdToValidate);
        if(office.isEmpty()) {
            log.error("Substation office with id {} does not exist", officeIdToValidate);
            return false;
        }
        return true;
    }
}
