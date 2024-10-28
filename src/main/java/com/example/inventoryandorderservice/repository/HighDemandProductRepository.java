package com.example.inventoryandorderservice.repository;

import com.example.inventoryandorderservice.model.HighDemandProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HighDemandProductRepository extends JpaRepository<HighDemandProduct, Long> {

    Optional<HighDemandProduct> findByProductId(Long aLong);
}