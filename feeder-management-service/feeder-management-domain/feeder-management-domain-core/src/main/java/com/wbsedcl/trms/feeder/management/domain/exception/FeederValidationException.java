package com.wbsedcl.trms.feeder.management.domain.exception;

public class FeederValidationException extends FeederDomainException{
    public FeederValidationException(String message) {
        super(message);
    }

    public FeederValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
