package com.wbsedcl.trms.substation.log.domain.exception;

import com.wbsedcl.trms.domain.exception.DomainException;

public class EnergyMeterValidationException extends DomainException {
    public EnergyMeterValidationException(String message) {
        super(message);
    }

    public EnergyMeterValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
