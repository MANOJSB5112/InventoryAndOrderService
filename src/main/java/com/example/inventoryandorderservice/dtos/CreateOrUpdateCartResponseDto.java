package com.example.inventoryandorderservice.dtos;

import com.example.inventoryandorderservice.dtos.ResponseStatus;
import com.example.inventoryandorderservice.model.Cart;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrUpdateCartResponseDto {
    private Cart cart;
    private ResponseStatus responseStatus;
}
