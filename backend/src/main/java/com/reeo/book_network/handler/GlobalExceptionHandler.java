package com.reeo.book_network.handler;


import com.reeo.book_network.exception.OperationNotPermittedException;
import com.reeo.book_network.exception.ResourceNotFoundException;
import com.reeo.book_network.exception.TokenExpiredException;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static com.reeo.book_network.handler.BusinessErrorCodes.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(LockedException.class)
  ResponseEntity<ExceptionResponse> handleException(LockedException exception) {
    return ResponseEntity
        .status(UNAUTHORIZED)
        .body(
            ExceptionResponse.builder()
                .businessErrorCode(ACCOUNT_LOCKED.getCode())
                .businessErrorDescription(ACCOUNT_LOCKED.getDescription())
                .error(exception.getMessage())
                .build()
        );
  }

  @ExceptionHandler(DisabledException.class)
  ResponseEntity<ExceptionResponse> handleException(DisabledException exception) {
    return ResponseEntity
        .status(UNAUTHORIZED)
        .body(
            ExceptionResponse.builder()
                .businessErrorDescription(ACCOUNT_DISABLED.getDescription())
                .businessErrorCode(ACCOUNT_DISABLED.getCode())
                .error(exception.getMessage())
                .build()
        );
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ExceptionResponse> handleException() {
    return ResponseEntity
        .status(UNAUTHORIZED)
        .body(
            ExceptionResponse.builder()
                .businessErrorCode(BAD_CREDENTIALS.getCode())
                .businessErrorDescription(BAD_CREDENTIALS.getDescription())
                .error(BAD_CREDENTIALS.getDescription())
                .build()
        );
  }

  @ExceptionHandler(MessagingException.class)
  public ResponseEntity<ExceptionResponse> handleException(MessagingException exp) {
    return ResponseEntity
        .status(INTERNAL_SERVER_ERROR)
        .body(
            ExceptionResponse.builder()
                .error(exp.getMessage())
                .build()
        );
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ExceptionResponse> handleException(ResourceNotFoundException exp) {
    return ResponseEntity
        .status(NOT_FOUND)
        .body(
            ExceptionResponse.builder()
                .businessErrorCode(RESOURCE_NOT_FOUND.getCode())
                .businessErrorDescription(RESOURCE_NOT_FOUND.getDescription())
                .error(exp.getMessage())
                .build()
        );
  }

  @ExceptionHandler(TokenExpiredException.class)
  public ResponseEntity<ExceptionResponse> handleException(TokenExpiredException exp) {
    return ResponseEntity
        .status(BAD_GATEWAY)
        .body(
            ExceptionResponse.builder()
                .businessErrorCode(TOKEN_EXPIRED.getCode())
                .businessErrorDescription(TOKEN_EXPIRED.getDescription())
                .error(exp.getMessage())
                .build()
        );
  }

  @ExceptionHandler(OperationNotPermittedException.class)
  public ResponseEntity<ExceptionResponse> handleException(OperationNotPermittedException exp) {
    return ResponseEntity
        .status(BAD_REQUEST)
        .body(
            ExceptionResponse.builder()
                .error(exp.getMessage())
                .build()
        );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
    Set<String> errors = new HashSet<>();
    exp.getBindingResult().getAllErrors()
        .forEach(error -> {
          var fieldName = ((FieldError) error).getField();
          var errorMessage = fieldName + " " + error.getDefaultMessage();
          errors.add(errorMessage);
        });

    return ResponseEntity
        .status(BAD_REQUEST)
        .body(
            ExceptionResponse.builder()
                .validationErrors(errors)
                .build()
        );
  }

  @SuppressWarnings("CallToPrintStackTrace")
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ExceptionResponse> handleException(HttpMessageNotReadableException exp) {
    exp.printStackTrace();
    return ResponseEntity
        .status(BAD_REQUEST)
        .body(
            ExceptionResponse.builder()
                .businessErrorDescription(BAD_REQUEST.getReasonPhrase())
                .error("Required request body is missing")
                .build()
        );
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
    exp.printStackTrace();
    return ResponseEntity
        .status(INTERNAL_SERVER_ERROR)
        .body(
            ExceptionResponse.builder()
                .businessErrorDescription("Internal error, please contact the admin")
                .build()
        );
  }

}
