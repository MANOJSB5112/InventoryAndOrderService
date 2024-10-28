package com.example.inventoryandorderservice.OrderPackage.OrderService;

import com.example.inventoryandorderservice.exceptions.AddressNotMatchForUser;
import com.example.inventoryandorderservice.exceptions.HighDemandProductException;
import com.example.inventoryandorderservice.exceptions.OutOfStockException;
import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.Customer;
import com.example.inventoryandorderservice.model.Order;
import com.example.inventoryandorderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    private PlaceOrderFacade placeOrderFacade;
    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(PlaceOrderFacade placeOrderFacade,OrderRepository orderRepository)
    {
        this.placeOrderFacade=placeOrderFacade;
        this.orderRepository=orderRepository;
    }
    @Override
    @Transactional
    public Order placeOrder(Customer customer, long addressId) throws AddressNotMatchForUser, ResourceNotFoundException, OutOfStockException, HighDemandProductException {
        return placeOrderFacade.placeOrder(customer,addressId);
    }
    @Override
    public List<Order> getAllOrdersForCustomer(Customer customer) throws ResourceNotFoundException {
        List<Order> orders=orderRepository.findAllByCustomerId(customer.getUserId());
        if(orders.isEmpty())
        {
            throw new ResourceNotFoundException(customer.getName()+" has not placed any orders yet.");
        }
        return orders;
    }


}
