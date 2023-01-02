package com.wbsedcl.trms.substation.log.domain.exception;

public class InterruptionValidationException extends InterruptionDomainException{
    public InterruptionValidationException(String message) {
        super(message);
    }

    public InterruptionValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
