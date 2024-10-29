package com.example.inventoryandorderservice.Kafka.controller;

import com.example.inventoryandorderservice.Kafka.dtos.UpdateNewUserDto;
import com.example.inventoryandorderservice.Kafka.service.KafkaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

@Controller
public class KafkaController {
    private ObjectMapper objectMapper;
    private KafkaService kafkaService;


    public KafkaController(ObjectMapper objectMapper,KafkaService kafkaService)
    {
        this.objectMapper=objectMapper;
        this.kafkaService=kafkaService;
    }

    @KafkaListener(topics= "createNewUser", groupId = "inventoryAndOrderService")
    public void createNewUser(String message) throws JsonProcessingException {
        UpdateNewUserDto newUser=objectMapper.readValue(message,UpdateNewUserDto.class);
        long userId=newUser.getUserId();
        String name=newUser.getName();
        String email= newUser.getEmail();
        String phoneNumber= newUser.getPhoneNumber();
        String roleName= newUser.getRoleName();

        kafkaService.createNewCustomer(userId,name,email,phoneNumber,roleName);
    }
}
