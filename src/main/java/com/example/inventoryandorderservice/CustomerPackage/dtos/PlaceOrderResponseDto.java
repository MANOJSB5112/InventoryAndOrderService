package com.example.inventoryandorderservice.CustomerPackage.dtos;

import com.example.inventoryandorderservice.dtos.ResponseStatus;
import com.example.inventoryandorderservice.model.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceOrderResponseDto {
    private Order order;
    private ResponseStatus status;
}
