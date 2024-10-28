package com.example.inventoryandorderservice.CustomerPackage.dtos;

import com.example.inventoryandorderservice.dtos.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelOrderResponseDto {
    private String message;
    private ResponseStatus responseStatus;
}
