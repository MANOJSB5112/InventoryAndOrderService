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
@Entity(name = "user_table")
public class User extends BaseModel{
    private String name;
    private String email;
    @OneToMany(mappedBy = "user")
    private List<Address> addresses;
    @OneToMany(mappedBy = "user")
    private List<Order> orders;
    @Enumerated(EnumType.ORDINAL)
    private UserType userType;
}
