package com.skyinfo.weatherforecast;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionController  extends ResponseEntityExceptionHandler {

    public static final Logger log  = LoggerFactory.getLogger(GlobalExceptionController.class);
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDTO handleGenericException(HttpServletRequest request, Exception exception){
        ErrorDTO dtoError = new ErrorDTO();
        dtoError.setTimeStamp(new Date());
        dtoError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        dtoError.addErrors(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        dtoError.setPath(request.getServletPath());
        log.error(exception.getMessage(),exception);
        return dtoError;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorDTO error = new ErrorDTO();
        error.setTimeStamp(new Date());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setPath(request.getContextPath());
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fieldError -> {
            error.addErrors(fieldError.getDefaultMessage());
        });
        return new ResponseEntity<>(error, headers, status);
        }
}
