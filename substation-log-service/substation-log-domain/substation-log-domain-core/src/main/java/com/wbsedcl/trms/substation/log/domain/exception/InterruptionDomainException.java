package com.wbsedcl.trms.substation.log.domain.exception;

import com.wbsedcl.trms.domain.exception.DomainException;

public class InterruptionDomainException extends DomainException {

    public InterruptionDomainException(String message) {
        super(message);
    }

    public InterruptionDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
