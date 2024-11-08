package com.example.inventoryandorderservice.CustomerPackage.dtos;

import com.example.inventoryandorderservice.dtos.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetOrdersListResponseDto {
    private List<CustomOrder> customOrderList;
    private ResponseStatus responseStatus;
}
