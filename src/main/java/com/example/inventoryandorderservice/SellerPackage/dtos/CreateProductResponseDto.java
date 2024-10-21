package com.example.inventoryandorderservice.SellerPackage.dtos;

import com.example.inventoryandorderservice.dtos.ResponseStatus;
import com.example.inventoryandorderservice.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductResponseDto {
    private Product product;
    private ResponseStatus responseStatus;
}
