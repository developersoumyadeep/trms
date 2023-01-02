package com.wbsedcl.trms.substation.log.domain.exception;

public class LoadRecordValidationException extends RuntimeException {
    public LoadRecordValidationException(String message) {
        super(message);
    }

    public LoadRecordValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
