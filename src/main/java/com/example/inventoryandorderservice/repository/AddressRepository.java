package com.example.inventoryandorderservice.repository;

import com.example.inventoryandorderservice.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Override
    Optional<Address> findById(Long addressId);
}