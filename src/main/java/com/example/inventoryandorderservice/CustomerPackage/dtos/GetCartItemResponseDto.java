package com.example.inventoryandorderservice.CustomerPackage.dtos;

import com.example.inventoryandorderservice.dtos.ResponseStatus;
import com.example.inventoryandorderservice.model.CartItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetCartItemResponseDto {
    private List<CartItem> cartItems;
    private ResponseStatus responseStatus;
}
