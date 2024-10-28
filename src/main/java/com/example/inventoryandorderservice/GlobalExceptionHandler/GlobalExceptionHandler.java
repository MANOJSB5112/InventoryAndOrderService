package com.example.inventoryandorderservice.GlobalExceptionHandler;

import com.example.inventoryandorderservice.dtos.ExceptionDto;
import com.example.inventoryandorderservice.dtos.ResponseStatus;
import com.example.inventoryandorderservice.exceptions.*;
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

    @ExceptionHandler(AddressNotMatchForUser.class)
    public ResponseEntity<ExceptionDto> handleAddressMisMatchException(AddressNotMatchForUser ex)
    {
        ExceptionDto exceptionDto=new ExceptionDto();
        exceptionDto.setMessage(ex.getMessage());
        exceptionDto.setResponseStatus(ResponseStatus.FAILURE);
        return new ResponseEntity<>(exceptionDto, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(HighDemandProductException.class)
    public ResponseEntity<ExceptionDto> handleAddressMisMatchException(HighDemandProductException ex)
    {
        ExceptionDto exceptionDto=new ExceptionDto();
        exceptionDto.setMessage(ex.getMessage());
        exceptionDto.setResponseStatus(ResponseStatus.FAILURE);
        return new ResponseEntity<>(exceptionDto, HttpStatus.TOO_MANY_REQUESTS);
    }
    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<ExceptionDto> handleAddressMisMatchException(OutOfStockException ex)
    {
        ExceptionDto exceptionDto=new ExceptionDto();
        exceptionDto.setMessage(ex.getMessage());
        exceptionDto.setResponseStatus(ResponseStatus.FAILURE);
        return new ResponseEntity<>(exceptionDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDto> handleAccessDeniedException(AccessDeniedException ex) {
        ExceptionDto exceptionDto=new ExceptionDto();
        exceptionDto.setMessage(ex.getMessage());
        exceptionDto.setResponseStatus(ResponseStatus.FAILURE);
        return new ResponseEntity<>(exceptionDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleGlobalException(Exception ex) {
        ExceptionDto exceptionDto=new ExceptionDto();
        exceptionDto.setMessage(ex.getMessage());
        exceptionDto.setResponseStatus(ResponseStatus.FAILURE);
        return new ResponseEntity<>(exceptionDto, HttpStatus.FORBIDDEN);
    }
}
