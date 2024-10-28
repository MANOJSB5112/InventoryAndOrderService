package com.example.inventoryandorderservice.repository;

import com.example.inventoryandorderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

//    List<Order> findAllByOrderDetails
//    List<Order> findByOrderDetailsProductSeller(Seller seller);
  Order save(Order order);

  List<Order> findAllByCustomerId(long userId);
}