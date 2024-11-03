package com.example.inventoryandorderservice.CustomerPackage.dtos;

import com.example.inventoryandorderservice.dtos.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetOrderResponseDto {
    private CustomOrder customOrder;
    private ResponseStatus responseStatus;
}
