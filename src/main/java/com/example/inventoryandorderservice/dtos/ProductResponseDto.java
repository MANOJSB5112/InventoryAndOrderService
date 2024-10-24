package com.example.inventoryandorderservice.dtos;

import com.example.inventoryandorderservice.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private Product product;
    private ResponseStatus responseStatus;
}
