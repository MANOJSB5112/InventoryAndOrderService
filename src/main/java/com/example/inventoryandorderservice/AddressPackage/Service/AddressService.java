package com.example.inventoryandorderservice.AddressPackage.Service;

import com.example.inventoryandorderservice.CustomerPackage.dtos.AddAddressRequestDto;
import com.example.inventoryandorderservice.exceptions.AddressNotMatchForUser;
import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.Address;

public interface AddressService {
    Address validateAddressForUserAndGet(long userId,long addressId) throws ResourceNotFoundException, AddressNotMatchForUser;
    Address addNewAddress(long userId, AddAddressRequestDto addAddressRequestDto);
}
