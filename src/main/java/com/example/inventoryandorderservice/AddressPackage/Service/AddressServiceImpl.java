package com.example.inventoryandorderservice.AddressPackage.Service;

import com.example.inventoryandorderservice.CustomerPackage.dtos.AddAddressRequestDto;
import com.example.inventoryandorderservice.exceptions.AddressNotMatchForUser;
import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.Address;
import com.example.inventoryandorderservice.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService{
    private AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository)
    {
        this.addressRepository=addressRepository;
    }
    @Override
    public Address validateAddressForUserAndGet(long userId, long addressId) throws ResourceNotFoundException, AddressNotMatchForUser {
        Optional<Address> addressOptional=addressRepository.findById(addressId);
        if(addressOptional.isEmpty())
        {
            throw new ResourceNotFoundException("Address Not found with the address Id "+addressId);
        }
        Address address=addressOptional.get();
        if(address.getUserId()!=userId)
        {
            throw new AddressNotMatchForUser("Address Id "+addressId+" not match for the given user Id "+userId);
        }
        return address;
    }

    @Override
    public Address addNewAddress(long userId, AddAddressRequestDto addAddressRequestDto) {
        Address address=new Address();
        address.setUserId(userId);
        address.setBuilding(addAddressRequestDto.getBuilding());
        address.setFloor(addAddressRequestDto.getFloor());
        address.setHouseNo(addAddressRequestDto.getHouseNo());
        address.setStreet(addAddressRequestDto.getStreet());
        address.setCity(addAddressRequestDto.getCity());
        address.setState(addAddressRequestDto.getState());
        address.setCountry(addAddressRequestDto.getCountry());
        address.setZipCode(addAddressRequestDto.getZipCode());
        address.setLatitude(addAddressRequestDto.getLatitude());
        address.setLongitude(addAddressRequestDto.getLongitude());

        return addressRepository.save(address);
    }
}
