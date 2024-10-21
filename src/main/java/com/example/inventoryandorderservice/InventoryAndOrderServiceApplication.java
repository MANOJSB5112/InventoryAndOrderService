package com.example.inventoryandorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InventoryAndOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryAndOrderServiceApplication.class, args);
    }

}
