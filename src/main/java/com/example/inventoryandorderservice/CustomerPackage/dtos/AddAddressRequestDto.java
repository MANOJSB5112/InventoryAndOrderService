package com.example.inventoryandorderservice.CustomerPackage.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAddressRequestDto {
    private String houseNo;
    private int floor;
    private String building;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private double latitude;
    private double longitude;
}
