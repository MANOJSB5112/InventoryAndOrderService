package com.example.inventoryandorderservice.OrderPackage.OrderService;

import com.example.inventoryandorderservice.exceptions.*;
import com.example.inventoryandorderservice.model.Customer;
import com.example.inventoryandorderservice.model.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Customer customer, long addressId) throws AddressNotMatchForUser, ResourceNotFoundException, OutOfStockException, HighDemandProductException;
    List<Order> getAllOrdersForCustomer(Customer customer) throws ResourceNotFoundException;
    Order getOrderByIdForCustomer(Customer customer,long orderId) throws ResourceNotFoundException, AccessDeniedException;
    String cancelOrderById(Customer customer, long orderId) throws ResourceNotFoundException, AccessDeniedException;
}
