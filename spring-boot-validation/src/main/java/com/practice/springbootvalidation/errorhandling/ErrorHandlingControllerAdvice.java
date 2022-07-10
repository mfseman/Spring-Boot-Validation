package com.practice.springbootvalidation.errorhandling;

import com.practice.springbootvalidation.models.response.ErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetails> handleConstraintViolationException(ConstraintViolationException e) {
        String firstFailedField = e.getConstraintViolations().stream().findFirst().get().getMessage();
        String invalidAttribute = e.getConstraintViolations().stream().findFirst().get().getInvalidValue().toString();
        log.error("ConstraintViolationException happened: {} - {}", firstFailedField, invalidAttribute);
        return generateErrorDetailsResponse(firstFailedField, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.error("MethodArgumentNotValidException happened: {}", errorMessage);
        return generateErrorDetailsResponse(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorDetails> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException happened: {}", e.getMessage());
        return generateErrorDetailsResponse(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorDetails> handleUnsupportedMediaTypeHandler(HttpMediaTypeNotSupportedException e) {
        log.error("HttpMediaTypeNotSupportedException happened: {}", e.getMessage());
        return generateErrorDetailsResponse(e.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGenericException(Exception e) {
        log.error("Exception happened: {}, stacktrace: {}", e.getMessage(), e);
        return generateErrorDetailsResponse("Unexpected exception occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorDetails> generateErrorDetailsResponse(String message, HttpStatus code) {
        ErrorDetails response = new ErrorDetails();
        response.setMessage(message);
        return ResponseEntity.status(code).body(response);
    }
}
