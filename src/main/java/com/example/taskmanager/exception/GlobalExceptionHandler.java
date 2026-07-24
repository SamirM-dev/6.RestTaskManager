package com.example.taskmanager.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.core.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(
                HttpStatus.METHOD_NOT_ALLOWED.value(), "METHOD_NOT_ALLOWED","HTTP-метод "+e.getMethod()+" не поддерживается по данному пути",request.getRequestURI()
        );

        String[] methods = e.getSupportedMethods();
        return  ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).header(HttpHeaders.ALLOW,String.join(", ",methods)).body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException e,HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),"NOT FOUND","Запрошенный путь не существует",request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException e,HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(
                HttpStatus.CONTINUE.value(), "CONFLICT", "Операция нарушает ограничение целостности данных", request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ErrorResponse> handlePropertyReference(PropertyReferenceException e,HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(
                400,"BAD REQUEST","Не корректное поле сортировки: "+e.getPropertyName(),request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(response);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request){
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),"INTERNAL SERVER ERROR","Problems with server",request.getRequestURI()
        );
        return ResponseEntity.internalServerError().body(response);
    }
}
