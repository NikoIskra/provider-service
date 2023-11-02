package com.provider.exception.handler;

import com.provider.exception.BadRequestException;
import com.provider.exception.ConflictException;
import com.provider.exception.NoContentException;
import com.provider.exception.NotFoundException;
import com.provider.model.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler
  protected ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
    ErrorResponse errorResponse = new ErrorResponse().ok(false).errorMessage(ex.getMessage());
    return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  protected ResponseEntity<ErrorResponse> handleConflictExcpetion(ConflictException ex) {
    ErrorResponse errorResponse = new ErrorResponse().ok(false).errorMessage(ex.getMessage());
    return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler
  protected ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse().ok(false).errorMessage(ex.getMessage());
    return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  protected ResponseEntity<ErrorResponse> handleJakartaConstraintViolationException(
      ConstraintViolationException ex) {
    ErrorResponse errorResponse = new ErrorResponse().ok(false).errorMessage(ex.getMessage());
    return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  protected ResponseEntity<ErrorResponse> handleNoContentException(NoContentException ex) {
    return new ResponseEntity<ErrorResponse>(HttpStatus.NO_CONTENT);
  }

  @ExceptionHandler
  protected ResponseEntity<ErrorResponse> test(MethodArgumentTypeMismatchException ex) {
    String errorMessage =
        "value not expected";
    if (ex.getMessage().contains("orderEnum")) {
      errorMessage = "value not expected, order can be ASC or DESC only!";
    }
    else if (ex.getMessage().contains("orderByEnum")) {
      errorMessage = "value not expected, order by can be created_at only!";
    }
    ErrorResponse errorResponse = new ErrorResponse().ok(false).errorMessage(errorMessage);
    return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse().ok(false).errorMessage("validation failed");
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse().ok(false).errorMessage(ex.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  protected ResponseEntity<ErrorResponse> handleInternalServerError(Exception ex) {
    ErrorResponse errorResponse =
        new ErrorResponse().ok(false).errorMessage(ExceptionUtils.getRootCause(ex).toString());
    return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
