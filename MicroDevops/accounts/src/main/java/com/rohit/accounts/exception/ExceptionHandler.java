package com.rohit.accounts.exception;

import com.rohit.accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandler {
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

}
