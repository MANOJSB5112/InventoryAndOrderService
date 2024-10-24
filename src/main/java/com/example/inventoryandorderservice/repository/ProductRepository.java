package com.example.inventoryandorderservice.repository;

import com.example.inventoryandorderservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    Optional<Product> findById(Long aLong);

    @Override
    List<Product> findAll();

    List<Product> findAllByCategoryId(Long id);


}