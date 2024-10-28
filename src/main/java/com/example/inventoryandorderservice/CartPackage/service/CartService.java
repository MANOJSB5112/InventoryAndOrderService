package com.example.inventoryandorderservice.CartPackage.service;

import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.Cart;
import com.example.inventoryandorderservice.model.CartItem;

import java.util.List;

public interface CartService {

    Cart addToCart(long userId, long productId, int quantity) throws ResourceNotFoundException;
    Cart updateCartItem(long userId, long productId, int quantity) throws ResourceNotFoundException;
    List<CartItem>  getCartItems(long userId) throws ResourceNotFoundException;
}
