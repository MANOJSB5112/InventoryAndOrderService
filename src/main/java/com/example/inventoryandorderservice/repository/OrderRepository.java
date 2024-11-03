package com.example.inventoryandorderservice.repository;

import com.example.inventoryandorderservice.model.Customer;
import com.example.inventoryandorderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

//    List<Order> findAllByOrderDetails
//    List<Order> findByOrderDetailsProductSeller(Seller seller);
  Order save(Order order);

  List<Order> findAllByCustomerId(long userId);

List<Order> findByCustomer(Customer customer);
  @Override
  Optional<Order> findById(Long orderId);
}