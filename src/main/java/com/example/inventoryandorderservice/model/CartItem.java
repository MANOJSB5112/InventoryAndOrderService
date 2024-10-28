package com.example.inventoryandorderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CartItem extends BaseModel{
    @ManyToOne
    private Product product;
    private Integer quantity;
    @ManyToOne
    private Cart cart;

}