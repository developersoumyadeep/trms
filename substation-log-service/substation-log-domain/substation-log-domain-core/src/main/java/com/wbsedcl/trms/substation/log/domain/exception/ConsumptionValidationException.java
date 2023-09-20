package com.wbsedcl.trms.substation.log.domain.exception;

import com.wbsedcl.trms.domain.exception.DomainException;

public class ConsumptionValidationException extends DomainException {
    public ConsumptionValidationException(String message) {
        super(message);
    }

    public ConsumptionValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
