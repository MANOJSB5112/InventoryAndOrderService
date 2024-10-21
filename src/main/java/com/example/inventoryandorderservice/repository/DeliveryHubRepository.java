package com.example.inventoryandorderservice.repository;

import com.example.inventoryandorderservice.model.DeliveryHub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryHubRepository extends JpaRepository<DeliveryHub, Long> {
}