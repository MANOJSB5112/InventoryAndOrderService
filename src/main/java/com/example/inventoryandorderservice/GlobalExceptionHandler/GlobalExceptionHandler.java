package com.example.inventoryandorderservice.GlobalExceptionHandler;

import com.example.inventoryandorderservice.SellerPackage.exceptions.CategoryNotFoundException;
import com.example.inventoryandorderservice.SellerPackage.exceptions.UserNotFoundException;
import com.example.inventoryandorderservice.dtos.ExceptionDto;
import com.example.inventoryandorderservice.dtos.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleUserNotFoundException(UserNotFoundException ex) {
        ExceptionDto exceptionDto=new ExceptionDto();
        exceptionDto.setMessage(ex.getMessage());
        exceptionDto.setResponseStatus(ResponseStatus.FAILURE);
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleUserNotFoundException(CategoryNotFoundException ex) {
        ExceptionDto exceptionDto=new ExceptionDto();
        exceptionDto.setMessage(ex.getMessage());
        exceptionDto.setResponseStatus(ResponseStatus.FAILURE);
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }
}
