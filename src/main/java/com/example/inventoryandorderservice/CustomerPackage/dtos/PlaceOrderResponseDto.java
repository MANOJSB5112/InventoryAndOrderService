package com.example.inventoryandorderservice.CustomerPackage.dtos;

import com.example.inventoryandorderservice.dtos.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceOrderResponseDto {
    private CustomOrder customOrder;
    private ResponseStatus status;
}
