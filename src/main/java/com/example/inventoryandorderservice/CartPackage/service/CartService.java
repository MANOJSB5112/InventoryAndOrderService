package com.example.inventoryandorderservice.CartPackage.service;

import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.Cart;

public interface CartService {
    Cart addOrUpdateCartItem(long userId, long productId, int quantity) throws Exception;
    Cart getCartItems(long userId) throws ResourceNotFoundException;
    Double getCartValue(long userId) throws ResourceNotFoundException;
}
