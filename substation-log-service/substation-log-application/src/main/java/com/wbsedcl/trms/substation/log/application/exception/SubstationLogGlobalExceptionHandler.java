package com.wbsedcl.trms.substation.log.application.exception;

import com.wbsedcl.trms.application.exception.handler.ErrorDTO;
import com.wbsedcl.trms.substation.log.domain.dto.create.CommandValidationException;
import com.wbsedcl.trms.substation.log.domain.exception.*;
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
public class SubstationLogGlobalExceptionHandler {

    @ExceptionHandler(InterruptionDomainException.class)
    public ResponseEntity<ErrorDTO> handleException(InterruptionDomainException exception) {
        List<String> messages = new ArrayList<>();
        messages.add(exception.getMessage());
        log.info("handling new exception :"+exception.getMessage());
        ErrorDTO errorDTO = new ErrorDTO(messages, HttpStatus.INTERNAL_SERVER_ERROR.value(), System.currentTimeMillis());
        return new ResponseEntity<>(errorDTO,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InterruptionValidationException.class)
    public ResponseEntity<ErrorDTO> handleException(InterruptionValidationException exception) {
        List<String> messages = new ArrayList<>();
        messages.add(exception.getMessage());
        log.info("handling new exception :"+exception.getMessage());
        ErrorDTO errorDTO = new ErrorDTO(messages, HttpStatus.BAD_REQUEST.value(), System.currentTimeMillis());
        return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(InterruptionNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleException(InterruptionNotFoundException exception) {
        List<String> messages = new ArrayList<>();
        messages.add(exception.getMessage());
        log.info("handling new exception :"+exception.getMessage());
        ErrorDTO errorDTO = new ErrorDTO(messages, HttpStatus.NOT_FOUND.value(), System.currentTimeMillis());
        return new ResponseEntity<>(errorDTO,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleException(UserNotFoundException exception) {
        List<String> messages = new ArrayList<>();
        messages.add(exception.getMessage());
        log.info("handling new exception :"+exception.getMessage());
        ErrorDTO errorDTO = new ErrorDTO(messages, HttpStatus.NOT_FOUND.value(), System.currentTimeMillis());
        return new ResponseEntity<>(errorDTO,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConsumptionValidationException.class)
    public ResponseEntity<ErrorDTO> handleException(ConsumptionValidationException exception) {
        List<String> messages = new ArrayList<>();
        messages.add(exception.getMessage());
        log.info("handling new exception :"+exception.getMessage());
        ErrorDTO errorDTO = new ErrorDTO(messages, HttpStatus.BAD_REQUEST.value(), System.currentTimeMillis());
        return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoadRecordValidationException.class)
    public ResponseEntity<ErrorDTO> handleException(LoadRecordValidationException exception) {
        List<String> messages = new ArrayList<>();
        messages.add(exception.getMessage());
        log.info("handling new exception :"+exception.getMessage());
        ErrorDTO errorDTO = new ErrorDTO(messages, HttpStatus.BAD_REQUEST.value(), System.currentTimeMillis());
        return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<String> messages = new ArrayList<>();
        log.info("handling new exception :"+exception.getMessage());
        bindingResult.getFieldErrors().forEach(error->{messages.add(error.getField()+": "+error.getDefaultMessage());});
        ErrorDTO errorDTO = new ErrorDTO(messages, HttpStatus.BAD_REQUEST.value(), System.currentTimeMillis());
        return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CommandValidationException.class)
    public ResponseEntity<ErrorDTO> handleException(CommandValidationException exception) {
        List<String> messages = new ArrayList<>();
        messages.add(exception.getMessage());
        log.info("handling new exception :"+exception.getMessage());
        ErrorDTO errorDTO = new ErrorDTO(messages, HttpStatus.BAD_REQUEST.value(), System.currentTimeMillis());
        return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public void handleException(Exception exc) {
        exc.printStackTrace();
    }





}
