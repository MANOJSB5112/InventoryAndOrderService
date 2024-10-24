package com.example.inventoryandorderservice.GlobalExceptionHandler;

import com.example.inventoryandorderservice.dtos.ExceptionDto;
import com.example.inventoryandorderservice.dtos.ResponseStatus;
import com.example.inventoryandorderservice.exceptions.AccessDeniedException;
import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ExceptionDto exceptionDto=new ExceptionDto();
        exceptionDto.setMessage(ex.getMessage());
        exceptionDto.setResponseStatus(ResponseStatus.FAILURE);
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDto> handleAccessDeniedException(AccessDeniedException ex) {
        ExceptionDto exceptionDto=new ExceptionDto();
        exceptionDto.setMessage(ex.getMessage());
        exceptionDto.setResponseStatus(ResponseStatus.FAILURE);
        return new ResponseEntity<>(exceptionDto, HttpStatus.FORBIDDEN);
    }
}
