package com.wbsedcl.trms.substation.log.domain.exception;

public class ConsumptionValidationException extends RuntimeException {
    public ConsumptionValidationException(String message) {
        super(message);
    }

    public ConsumptionValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
