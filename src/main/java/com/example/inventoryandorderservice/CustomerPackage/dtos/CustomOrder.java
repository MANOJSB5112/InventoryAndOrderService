package com.example.inventoryandorderservice.CustomerPackage.dtos;

import com.example.inventoryandorderservice.model.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomOrder {
    private long orderId;
    private long userId;
    private String customerName;
    private String address;
    private List<CustomOrderDetail> customOrderDetails;
    private OrderStatus orderStatus;
    private Double totalAmount;
}
