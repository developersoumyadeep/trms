package com.wbsedcl.trms.substation.log.domain.exception;

import com.wbsedcl.trms.domain.exception.DomainException;

public class EnergyMeterReadingValidationException extends DomainException {
    public EnergyMeterReadingValidationException(String message) {
        super(message);
    }

    public EnergyMeterReadingValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
