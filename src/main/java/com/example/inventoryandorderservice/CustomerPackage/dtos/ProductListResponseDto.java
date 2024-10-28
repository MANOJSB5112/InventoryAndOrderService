package com.example.inventoryandorderservice.CustomerPackage.dtos;

import com.example.inventoryandorderservice.dtos.ResponseStatus;
import com.example.inventoryandorderservice.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductListResponseDto {
    private List<Product> products;
    private ResponseStatus responseStatus;
}
