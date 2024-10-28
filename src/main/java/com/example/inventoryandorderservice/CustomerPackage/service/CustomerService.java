package com.example.inventoryandorderservice.CustomerPackage.service;

import com.example.inventoryandorderservice.exceptions.*;
import com.example.inventoryandorderservice.model.*;

import java.util.List;

public interface CustomerService {
    List<Product> getAllProducts();
    Product getProductById(Long productId) throws ResourceNotFoundException;

    List<Product> getProductByCategoryId(Long categoryId) throws ResourceNotFoundException;

    Cart addToCart(long userId, long productId, int quantity) throws ResourceNotFoundException;
    Cart updateCartItem(long userId, long productId, int quantity) throws ResourceNotFoundException, OutOfStockException, HighDemandProductException;
    List<CartItem>  getCartItems(long userId) throws ResourceNotFoundException;
    Order placeOrder(long userId, long addressId) throws ResourceNotFoundException, AddressNotMatchForUser, OutOfStockException, HighDemandProductException;
    Customer validateAndGetCustomer(long userId) throws ResourceNotFoundException;

    List<Order> getAllOrdersForCustomer(long userId) throws ResourceNotFoundException;

    Order getOrderByIdForCustomer(long userId,long orderId) throws ResourceNotFoundException, AccessDeniedException;

    String cancelOrderById(long userId, long orderId) throws ResourceNotFoundException, AccessDeniedException;
}
