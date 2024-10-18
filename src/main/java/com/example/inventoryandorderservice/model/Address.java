package com.example.inventoryandorderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Address extends BaseModel{
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String building;
    private int floor;
    private String roomNo;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private double latitude;
    private double longitude;
}