package com.provider.exception.handler;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.provider.exception.BadRequestException;
import com.provider.exception.ConflictException;
import com.provider.exception.NotFoundException;
import com.provider.model.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleBadRequestException (BadRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse()
        .ok(false)
        .errorMessage(ex.getMessage());
        return new ResponseEntity<ErrorResponse> (errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleConflictExcpetion (ConflictException ex) {
        ErrorResponse errorResponse = new ErrorResponse()
        .ok(false)
        .errorMessage(ex.getMessage());
        return new ResponseEntity<ErrorResponse> (errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleNotFoundException (NotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse()
        .ok(false)
        .errorMessage(ex.getMessage());
        return new ResponseEntity<ErrorResponse> (errorResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse()
        .ok(false)
        .errorMessage("validation failed");
        return new ResponseEntity<> (errorResponse, HttpStatus.BAD_REQUEST);
    }

    

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse()
        .ok(false)
        .errorMessage(ex.getMessage());
        return new ResponseEntity<> (errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleInternalServerError(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse()
        .ok(false)
        .errorMessage(ExceptionUtils.getRootCause(ex).toString());
        return new ResponseEntity<ErrorResponse> (errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}