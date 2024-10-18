package com.example.inventoryandorderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Seller extends BaseModel{
    private String name;
    private String email;
    @OneToOne
    private Address address;
}