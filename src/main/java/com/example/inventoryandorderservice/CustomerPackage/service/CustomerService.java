package com.example.inventoryandorderservice.CustomerPackage.service;

import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.Cart;
import com.example.inventoryandorderservice.model.CartItem;
import com.example.inventoryandorderservice.model.Product;

import java.util.List;

public interface CustomerService {
    List<Product> getAllProducts();
    Product getProductById(Long productId) throws ResourceNotFoundException;

    List<Product> getProductByCategoryId(Long categoryId) throws ResourceNotFoundException;

    Cart addToCart(long userId, long productId, int quantity) throws ResourceNotFoundException;
    Cart updateCartItem(long userId, long productId, int quantity) throws ResourceNotFoundException;
    List<CartItem>  getCartItems(long userId) throws ResourceNotFoundException;
}
