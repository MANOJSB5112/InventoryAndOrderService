package com.example.inventoryandorderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "customer_table")
public class Customer extends BaseModel{
    private long userId;
    private String name;
    private String email;
    @OneToMany(mappedBy = "customer")
    private List<Address> addresses;
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
    @Enumerated(EnumType.ORDINAL)
    private UserType userType;
}
