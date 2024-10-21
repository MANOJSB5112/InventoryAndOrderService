package com.example.inventoryandorderservice.SellerPackage.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequestDto {
    private Long categoryId;
    private String name;
    private String description;
    private double price;
}
