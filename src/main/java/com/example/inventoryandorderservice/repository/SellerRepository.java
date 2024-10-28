package com.example.inventoryandorderservice.repository;

import com.example.inventoryandorderservice.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    @Override
    Optional<Seller> findById(Long id);
    Optional<Seller> findByUserId(long userId);
}