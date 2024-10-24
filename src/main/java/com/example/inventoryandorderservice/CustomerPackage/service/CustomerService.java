package com.example.inventoryandorderservice.CustomerPackage.service;

import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.Product;

import java.util.List;

public interface CustomerService {
    List<Product> getAllProducts();
    Product getProductById(Long productId) throws ResourceNotFoundException;

    List<Product> getProductByCategoryId(Long categoryId) throws ResourceNotFoundException;
}
