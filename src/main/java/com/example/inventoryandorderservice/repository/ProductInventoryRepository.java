package com.example.inventoryandorderservice.repository;

import com.example.inventoryandorderservice.model.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long> {

    Optional<ProductInventory> findByProductId(long productId);
}