package com.example.inventoryandorderservice.exceptions;

public class ResourceNotFoundException extends Exception{

    public ResourceNotFoundException(String message)
    {
        super(message);
    }
}
