package com.example.inventoryandorderservice.model;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Entity
public class Address extends BaseModel{
    private long userId;
    private String building;
    private int floor;
    private String houseNo;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private double latitude;
    private double longitude;


    public String toString()
    {
        return houseNo+" floorNo "+floor+" "+building+" "+street+" "+city+" "+state+" "+country
                +" zipcode "+zipCode+" latitude "+latitude+" longitude "+longitude;
    }
}