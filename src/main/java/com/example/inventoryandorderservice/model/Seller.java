package com.example.inventoryandorderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Seller extends BaseModel{
    private long userId;
    private String name;
    private String email;
    private String phoneNumber;
    @Enumerated(EnumType.ORDINAL)
    private UserType userType;
}