package com.example.inventoryandorderservice.EventProcessorPackage;

public interface EventProcessor {
    void createNewCustomer(long userId,String name,String email,String  phoneNumber,String roleName );
}
