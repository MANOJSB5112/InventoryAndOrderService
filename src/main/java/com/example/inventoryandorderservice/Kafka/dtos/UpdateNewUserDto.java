package com.example.inventoryandorderservice.Kafka.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateNewUserDto {
    private long userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String roleName;
}
