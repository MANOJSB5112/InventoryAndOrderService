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
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    private Address deliveryAddress;
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;
}