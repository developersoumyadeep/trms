package com.wbsedcl.trms.substation.log.domain.exception;

import com.wbsedcl.trms.domain.exception.DomainException;

public class UserNotFoundException extends DomainException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
