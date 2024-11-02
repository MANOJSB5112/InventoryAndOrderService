package com.example.inventoryandorderservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Cart extends BaseModel{
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;
    private Double totalPrice;
    private long userId;
}