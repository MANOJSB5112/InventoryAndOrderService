package com.example.inventoryandorderservice.Kafka.service;

import com.example.inventoryandorderservice.EventProcessorPackage.EventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImpl implements KafkaService{
    private EventProcessor eventProcessor;

    @Autowired
    public KafkaServiceImpl(EventProcessor eventProcessor)
    {
        this.eventProcessor=eventProcessor;
    }

    @Override
    public void createNewCustomer(long userId, String name, String email, String phoneNumber, String roleName) {
        eventProcessor.createNewCustomer(userId,name,email,phoneNumber,roleName);
    }
}
