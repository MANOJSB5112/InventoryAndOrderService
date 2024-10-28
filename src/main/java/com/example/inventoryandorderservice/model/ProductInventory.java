package com.example.inventoryandorderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ProductInventory extends BaseModel{
    @OneToOne
    private Product product;
    private int quantity;
    @Version
    private int version;
}