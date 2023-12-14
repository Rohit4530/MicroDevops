package com.rohit.accounts.exception;

import com.rohit.accounts.dto.ErrorResponseDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.*;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be of 10 digit")
    @org.springframework.web.bind.annotation.ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto>handleCustomerAlreadyExistException(CustomerAlreadyExistException exception, WebRequest request){
       ErrorResponseDto errorResponse=new ErrorResponseDto(
               request.getDescription(false),
               HttpStatus.BAD_REQUEST,
               exception.getMessage(),
               LocalDateTime.now()
       );
       return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto>ResourceNotExistException(ResourceNotFoundException exception, WebRequest request){
        ErrorResponseDto errorResponse=new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto>handleExceptions(Exception exception, WebRequest request){
        ErrorResponseDto errorResponse=new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String,String>validationErrors=new HashMap<>();
        List<ObjectError>validationErrorList=ex.getBindingResult().getAllErrors();
        validationErrorList.forEach((errors)->{
            String fieldName=((FieldError)errors).getField();
            String validationMsg=errors.getDefaultMessage();
            validationErrors.put(fieldName,validationMsg);
        });
       return  new ResponseEntity<>(validationErrors,HttpStatus.BAD_REQUEST);
    }
}
