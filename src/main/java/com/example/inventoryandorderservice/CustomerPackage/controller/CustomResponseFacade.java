package com.example.inventoryandorderservice.CustomerPackage.controller;

import com.example.inventoryandorderservice.CustomerPackage.dtos.CustomCartItem;
import com.example.inventoryandorderservice.CustomerPackage.dtos.CustomOrder;
import com.example.inventoryandorderservice.CustomerPackage.dtos.CustomOrderDetail;
import com.example.inventoryandorderservice.CustomerPackage.dtos.CustomProduct;
import com.example.inventoryandorderservice.model.CartItem;
import com.example.inventoryandorderservice.model.Order;
import com.example.inventoryandorderservice.model.OrderDetail;
import com.example.inventoryandorderservice.model.Product;

import java.util.List;

public interface CustomResponseFacade {
    List<CustomProduct> getAllCustomProducts( List<Product> products);
     CustomProduct getCustomProduct(Product product);
     List<CustomCartItem> getCustomCartItemList(List<CartItem> cartItems);
     List<CustomOrderDetail> getCustomOrderDetailList(List<OrderDetail> orderDetails);
     CustomOrder getCustomerOrder(Order order);
    List<CustomOrder> getCustomOrderList(List<Order> orders);
}
