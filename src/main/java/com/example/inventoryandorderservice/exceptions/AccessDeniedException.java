package com.example.inventoryandorderservice.exceptions;

public class AccessDeniedException extends Exception{
    public AccessDeniedException(String message){
        super(message);
    }
}
