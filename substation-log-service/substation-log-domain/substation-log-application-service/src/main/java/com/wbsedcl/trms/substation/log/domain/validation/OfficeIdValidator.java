package com.wbsedcl.trms.substation.log.domain.validation;

import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.substation.log.domain.entity.Office;
import com.wbsedcl.trms.substation.log.domain.exception.InterruptionDomainException;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.OfficeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Optional;

@Component
@Slf4j
public class OfficeIdValidator implements ConstraintValidator<ValidOfficeId, String> {

    private final OfficeRepository  officeRepository;

    public OfficeIdValidator(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    @Override
    public void initialize(ValidOfficeId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String officeIdToValidate, ConstraintValidatorContext context) {
        if (officeIdToValidate == null || officeIdToValidate.equals("")){
            return false;
        }
        Optional<Office> office = officeRepository.findOffice(officeIdToValidate);
        if(office.isEmpty()) {
            log.error("Substation office with id {} does not exist", officeIdToValidate);
            return false;
        }
        return true;
    }
}
