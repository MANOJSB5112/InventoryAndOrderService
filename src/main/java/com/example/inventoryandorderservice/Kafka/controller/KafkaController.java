package com.example.inventoryandorderservice.Kafka.controller;

import com.example.inventoryandorderservice.Kafka.service.KafkaService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

//    @KafkaListener(topics= "createNewUser", groupId = "inventoryAndOrderService")
//    public void createNewUser(String message) throws JsonProcessingException {
//        NewUserExternalDto newUserExternalDto=objectMapper.readValue(message,NewUserExternalDto.class);
//        long userId=newUserExternalDto.getUserId();
//        String name=newUserExternalDto.getName();
//        String email= newUserExternalDto.getEmail();
//        String phoneNumber= newUserExternalDto.getPhoneNumber();
//        String roleName= newUserExternalDto.getRoleName();
//        kafkaService.createNewCustomer(userId,name,email,phoneNumber,roleName);
//    }
}
