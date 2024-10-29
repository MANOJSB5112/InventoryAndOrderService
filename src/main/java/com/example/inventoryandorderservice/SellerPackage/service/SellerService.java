package com.example.inventoryandorderservice.SellerPackage.service;

import com.example.inventoryandorderservice.exceptions.AccessDeniedException;
import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.Product;
import com.example.inventoryandorderservice.model.UserType;

public interface SellerService {

    Product addProduct(long sellerId,long categoryId,String name,String description,double price) throws ResourceNotFoundException;
    Product updateProduct(long sellerId,long ProductId,long categoryId,String name,String description,double price) throws ResourceNotFoundException, AccessDeniedException;

    void deleteProduct(long sellerId,long productId) throws ResourceNotFoundException, AccessDeniedException;

    void createNewSeller(long userId, String name, String email, String phoneNumber, UserType userType);

}
