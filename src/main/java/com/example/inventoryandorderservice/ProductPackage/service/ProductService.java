package com.example.inventoryandorderservice.ProductPackage.service;

import com.example.inventoryandorderservice.exceptions.AccessDeniedException;
import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.Product;
import com.example.inventoryandorderservice.model.Seller;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ProductService {
    Product addProduct(Seller seller, long categoryId, String name, String description, double price) throws ResourceNotFoundException;

    Product updateProduct(Seller seller,Long productId, Long categoryId, String name, String description, Double price) throws ResourceNotFoundException, AccessDeniedException;

    void deleteProduct(Seller seller,long productId) throws AccessDeniedException, ResourceNotFoundException;

    List<Product> getAllProducts();
    Product getProductById(Long productId) throws ResourceNotFoundException, JsonProcessingException;

    List<Product> getProductByCategoryId(Long categoryId) throws ResourceNotFoundException;

}
