package com.example.inventoryandorderservice.AwsSQS.service;

public interface AwsSQSService {
    void createNewCustomer(long userId,String name,String email,String  phoneNumber,String roleName );
}
