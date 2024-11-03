package com.example.inventoryandorderservice.CustomerPackage.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomProduct {
    private long productId;
    private String productName;
    private String description;
    private Double price;
    private String categoryName;
    private String sellerName;


}
