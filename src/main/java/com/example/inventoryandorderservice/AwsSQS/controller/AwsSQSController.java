package com.example.inventoryandorderservice.AwsSQS.controller;

import com.example.inventoryandorderservice.AwsSQS.service.AwsSQSService;
import com.example.inventoryandorderservice.dtos.NewUserExternalDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Controller;

@Controller
public class AwsSQSController {
    private ObjectMapper objectMapper;
    private AwsSQSService awsSQSService;

    @Autowired
    public AwsSQSController(ObjectMapper objectMapper,AwsSQSService awsSQSService)
    {
        this.objectMapper=objectMapper;
        this.awsSQSService=awsSQSService;
    }

    @SqsListener("create-newuser-queue")
    public void handleNewUserSignUpEvent(String snsMessage) throws JsonProcessingException {
        System.out.println("Raw message received: " + snsMessage);

        // Parse the outer SNS message
        JsonNode snsJsonNode = objectMapper.readTree(snsMessage);

        // Extract the inner "Message" field, which contains the JSON payload
        String message = snsJsonNode.get("Message").asText();

        // Deserialize the inner JSON into SendEmailDto
        NewUserExternalDto newUserExternalDto = objectMapper.readValue(message, NewUserExternalDto.class);
        long userId=newUserExternalDto.getUserId();
        String name=newUserExternalDto.getName();
        String email= newUserExternalDto.getEmail();
        String phoneNumber= newUserExternalDto.getPhoneNumber();
        String roleName= newUserExternalDto.getRoleName();

        System.out.println("Received here : " + userId + " " + name + " " + email+ " " + phoneNumber + " " + roleName);
        awsSQSService.createNewCustomer(userId,name,email, phoneNumber, roleName );
    }
}
