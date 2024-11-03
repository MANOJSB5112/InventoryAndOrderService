package com.example.inventoryandorderservice.CustomerPackage.dtos;

import com.example.inventoryandorderservice.dtos.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetCartItemResponseDto {
    private long cartId;
    List<CustomCartItem> customCartItems;
    private double totalCartValue;
    private ResponseStatus responseStatus;
}
