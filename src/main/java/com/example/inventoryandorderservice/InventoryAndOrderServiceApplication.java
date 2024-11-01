package com.example.inventoryandorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableSqs
public class InventoryAndOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryAndOrderServiceApplication.class, args);
    }

}
