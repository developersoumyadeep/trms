package com.wbsedcl.trms.substation.log.domain.exception;

import com.wbsedcl.trms.domain.exception.DomainException;

public class InterruptionNotFoundException extends InterruptionDomainException {
    public InterruptionNotFoundException(String message) {
        super(message);
    }

    public InterruptionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
