package com.example.inventoryandorderservice.CustomerPackage.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomCartItem {
    private long productId;
    private String productName;
    private int quantity;
    private double perQuantityPrice;
    private double productTotalPrice;

}
