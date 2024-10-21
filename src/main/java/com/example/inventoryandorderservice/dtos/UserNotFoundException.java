package com.example.inventoryandorderservice.dtos;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(String message)
    {
        super(message);
    }
}
