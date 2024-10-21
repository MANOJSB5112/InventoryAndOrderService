package com.example.inventoryandorderservice.ProductPackage.service;

import com.example.inventoryandorderservice.model.Category;
import com.example.inventoryandorderservice.model.Product;
import com.example.inventoryandorderservice.model.Seller;

public interface ProductService {
    Product addProduct(Seller seller, Category category, String name, String description, double price);
}
