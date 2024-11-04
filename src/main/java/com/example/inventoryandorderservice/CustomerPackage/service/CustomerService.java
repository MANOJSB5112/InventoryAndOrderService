package com.example.inventoryandorderservice.CustomerPackage.service;

import com.example.inventoryandorderservice.CustomerPackage.dtos.AddAddressRequestDto;
import com.example.inventoryandorderservice.exceptions.*;
import com.example.inventoryandorderservice.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface CustomerService {
    List<Product> getAllProducts();
    Product getProductById(Long productId) throws ResourceNotFoundException, JsonProcessingException;

    List<Product> getProductByCategoryId(Long categoryId) throws ResourceNotFoundException;
    Cart addOrUpdateCartItem(long userId, long productId, int quantity) throws Exception;
    Cart  getCartItems(long userId) throws ResourceNotFoundException;
    Order placeOrder(long userId, long addressId) throws ResourceNotFoundException, AddressNotMatchForUser, OutOfStockException, HighDemandProductException;
    Customer validateAndGetCustomer(long userId) throws ResourceNotFoundException;

    List<Order> getAllOrdersForCustomer(long userId) throws ResourceNotFoundException;

    Order getOrderByIdForCustomer(long userId,long orderId) throws ResourceNotFoundException, AccessDeniedException;

    String cancelOrderById(long userId, long orderId) throws ResourceNotFoundException, AccessDeniedException;

    void createNewCustomer(long userId,String name,String email,String phoneNumber,UserType userType);

    Address addNewAddress(long userId, AddAddressRequestDto addAddressRequestDto);
}
