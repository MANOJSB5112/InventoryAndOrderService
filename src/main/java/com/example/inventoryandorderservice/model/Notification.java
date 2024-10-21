package com.example.inventoryandorderservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Notification extends BaseModel{
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    private Customer customer;
    @Enumerated(EnumType.ORDINAL)
    private NotificationStatus status;
}