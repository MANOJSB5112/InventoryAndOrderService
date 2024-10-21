package com.example.inventoryandorderservice.SellerPackage.service;

import com.example.inventoryandorderservice.CategoryPackage.exceptions.CategoryNotFoundException;
import com.example.inventoryandorderservice.dtos.UserNotFoundException;
import com.example.inventoryandorderservice.model.Product;

public interface SellerService {

    Product addProduct(long userId,long categoryId,String name,String description,double price) throws UserNotFoundException, CategoryNotFoundException;
}
