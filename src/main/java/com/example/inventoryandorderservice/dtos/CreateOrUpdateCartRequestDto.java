package com.example.inventoryandorderservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrUpdateCartRequestDto {
   private Long userId;
   private Long productId;
   private Integer quantity;
}
