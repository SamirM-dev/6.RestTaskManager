package com.example.taskmanager.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> HandleIllegalArgument(IllegalArgumentException e,HttpServletRequest request){
        ErrorResponse response=new ErrorResponse(400,"BAD REQUEST",e.getMessage(),request.getRequestURI());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException e,HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(404,"NOT FOUND",e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpServletRequest request) {
        List<ErrorResponse.FieldError> fieldErrors = ex.getBindingResult().getFieldErrors().stream().map(field->new ErrorResponse.FieldError(field.getField(), field.getDefaultMessage())).toList();
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),"BAD REQUEST", "Incorrect input data",request.getRequestURI()
        );
        response.setFieldErrors(fieldErrors);
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExists(ResourceAlreadyExistsException e,HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(
                409,"ALREADY EXISTS",e.getMessage(),request.getRequestURI()
        );
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException e,HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),"UNAUTHORIZED",e.getMessage(),request.getRequestURI()
        );
        return ResponseEntity.status(401).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),"INTERNAL SERVER ERROR","Problems with server",request.getRequestURI()
        );
        return ResponseEntity.internalServerError().body(response);
    }
}
