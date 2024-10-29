package com.example.inventoryandorderservice.Kafka.service;

import com.example.inventoryandorderservice.CustomerPackage.service.CustomerService;
import com.example.inventoryandorderservice.SellerPackage.service.SellerService;
import com.example.inventoryandorderservice.model.UserType;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImpl implements KafkaService{
    private CustomerService customerService;
    private SellerService sellerService;

    public KafkaServiceImpl(CustomerService customerService,SellerService sellerService)
    {
        this.customerService=customerService;
        this.sellerService=sellerService;
    }

    @Override
    public void createNewCustomer(long userId, String name, String email, String phoneNumber, String roleName) {
        if(roleName.equals("Customer"))
        {
            customerService.createNewCustomer(userId,name,email,phoneNumber, UserType.CUSTOMER);
        }else {
            sellerService.createNewSeller(userId,name,email,phoneNumber,UserType.SELLER);
        }
    }
}
