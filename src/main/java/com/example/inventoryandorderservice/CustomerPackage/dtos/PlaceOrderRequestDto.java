package com.example.inventoryandorderservice.CustomerPackage.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceOrderRequestDto {
    private int userId;
    private int addressId;
}
