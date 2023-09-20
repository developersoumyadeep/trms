package com.wbsedcl.trms.application.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception exception) {
        List<String> messages = new ArrayList<>();
        exception.printStackTrace();
        messages.add(exception.getMessage());
        ErrorDTO errorDTO = new ErrorDTO(messages, HttpStatus.INTERNAL_SERVER_ERROR.value(), System.currentTimeMillis());
        return new ResponseEntity<>(errorDTO,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
