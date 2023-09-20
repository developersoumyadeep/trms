package com.wbsedcl.trms.feeder.management.domain.exception;

import com.wbsedcl.trms.domain.exception.DomainException;

public class FeederDomainException extends DomainException {
    public FeederDomainException(String message) {
        super(message);
    }

    public FeederDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
