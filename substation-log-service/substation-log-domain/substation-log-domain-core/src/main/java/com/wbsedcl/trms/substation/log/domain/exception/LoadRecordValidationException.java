package com.wbsedcl.trms.substation.log.domain.exception;

import com.wbsedcl.trms.domain.exception.DomainException;

public class LoadRecordValidationException extends DomainException {
    public LoadRecordValidationException(String message) {
        super(message);
    }

    public LoadRecordValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
