package com.wbsedcl.trms.feeder.management.application.exception;

import com.wbsedcl.trms.application.exception.handler.ErrorDTO;
import com.wbsedcl.trms.feeder.management.domain.exception.FeederDomainException;
import com.wbsedcl.trms.feeder.management.domain.exception.FeederValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class FeederManagementGlobalExceptionHandler {
    @ExceptionHandler(FeederDomainException.class)
    public ResponseEntity<ErrorDTO> handleException(FeederDomainException feederDomainException) {
        List<String> messages = new ArrayList<>();
        messages.add(feederDomainException.getMessage());
        ErrorDTO errorDTO = new ErrorDTO(messages, HttpStatus.INTERNAL_SERVER_ERROR.value(), System.currentTimeMillis());
        return new ResponseEntity<>(errorDTO,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FeederValidationException.class)
    public ResponseEntity<ErrorDTO> handleException(FeederValidationException feederValidationException) {
        List<String> messages = new ArrayList<>();
        messages.add(feederValidationException.getMessage());
        ErrorDTO errorDTO = new ErrorDTO(messages, HttpStatus.BAD_REQUEST.value(), System.currentTimeMillis());
        return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<String> messages = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(error->{messages.add(error.getField()+": "+error.getDefaultMessage());});
        ErrorDTO errorDTO = new ErrorDTO(messages, HttpStatus.BAD_REQUEST.value(), System.currentTimeMillis());
        return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);
    }
}

