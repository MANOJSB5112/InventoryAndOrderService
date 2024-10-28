package com.example.inventoryandorderservice.OrderPackage.OrderService;

import com.example.inventoryandorderservice.exceptions.AddressNotMatchForUser;
import com.example.inventoryandorderservice.exceptions.HighDemandProductException;
import com.example.inventoryandorderservice.exceptions.OutOfStockException;
import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.Customer;
import com.example.inventoryandorderservice.model.Order;

public interface PlaceOrderFacade {
    Order placeOrder(Customer customer, long addressId) throws AddressNotMatchForUser, ResourceNotFoundException, OutOfStockException, HighDemandProductException;
}
