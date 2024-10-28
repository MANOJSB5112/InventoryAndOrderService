package com.example.inventoryandorderservice.exceptions;

public class OutOfStockException extends Exception{
    public OutOfStockException(String message)
    {
        super(message);
    }
}
