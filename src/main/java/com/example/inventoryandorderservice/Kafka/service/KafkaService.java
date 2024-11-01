package com.example.inventoryandorderservice.Kafka.service;

public interface KafkaService {
   void createNewCustomer(long userId,String name,String email,String  phoneNumber,String roleName );
}
