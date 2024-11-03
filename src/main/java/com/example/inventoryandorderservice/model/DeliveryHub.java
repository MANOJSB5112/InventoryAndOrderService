package com.example.inventoryandorderservice.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DeliveryHub extends BaseModel{
//    @OneToOne
//    private Address address;
    private String name;
}