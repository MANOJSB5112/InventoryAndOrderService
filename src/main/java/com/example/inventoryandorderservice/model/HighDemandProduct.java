package com.example.inventoryandorderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class HighDemandProduct extends BaseModel{
    @OneToOne
    private Product product;
    private int maxQuantity;
}