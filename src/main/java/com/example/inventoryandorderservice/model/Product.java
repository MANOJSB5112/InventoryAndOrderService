package com.example.inventoryandorderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel{
    private String name;
    private String description;
    private Double price;
    @ManyToOne
    private Category category;
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
}