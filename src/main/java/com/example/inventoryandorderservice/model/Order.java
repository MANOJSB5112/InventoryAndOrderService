package com.example.inventoryandorderservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "order_table")
public class Order extends BaseModel{
    @ManyToOne
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    private Address deliveryAddress;
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;
    private double totalAmount;
}