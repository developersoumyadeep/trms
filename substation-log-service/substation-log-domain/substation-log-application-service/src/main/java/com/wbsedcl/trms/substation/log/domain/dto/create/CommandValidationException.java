package com.wbsedcl.trms.substation.log.domain.dto.create;

public class CommandValidationException extends RuntimeException {
    public CommandValidationException(String message) {
        super(message);
    }

    public CommandValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
