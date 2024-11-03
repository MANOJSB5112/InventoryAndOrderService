package com.example.inventoryandorderservice.OrderPackage.OrderService;

import com.example.inventoryandorderservice.exceptions.*;
import com.example.inventoryandorderservice.model.Customer;
import com.example.inventoryandorderservice.model.Order;
import com.example.inventoryandorderservice.model.OrderStatus;
import com.example.inventoryandorderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        List<Order> orders=orderRepository.findByCustomer(customer);
        if(orders.isEmpty())
        {
            throw new ResourceNotFoundException(customer.getName()+" has not placed any orders yet.");
        }
        return orders;
    }

    @Override
    public Order getOrderByIdForCustomer(Customer customer, long orderId) throws ResourceNotFoundException, AccessDeniedException {
        return verifyOrderForCustomerAndGet(customer,orderId);
    }

    @Override
    public String cancelOrderById(Customer customer, long orderId) throws ResourceNotFoundException, AccessDeniedException {
       Order order=verifyOrderForCustomerAndGet(customer,orderId);
        String message;
        if (order.getOrderStatus() == OrderStatus.SHIPPED || order.getOrderStatus() == OrderStatus.DELIVERED) {
            message = String.format("Order with the ID %d cannot be cancelled as it is already %s.", orderId, order.getOrderStatus());
        } else if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            message = String.format("Order with the ID %d is already cancelled.", orderId);
        } else {
            order.setOrderStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
            message = String.format("Order with the ID %d cancelled successfully.", orderId);
        }
        return message;
    }

    public Order verifyOrderForCustomerAndGet(Customer customer, long orderId) throws AccessDeniedException, ResourceNotFoundException {
        Optional<Order> orderOptional=orderRepository.findById(orderId);
        if(orderOptional.isEmpty())
        {
            throw new ResourceNotFoundException("Order not found with the orderId "+orderId);
        }
        Order order=orderOptional.get();
        if(order.getCustomer().getUserId()!=customer.getUserId())
        {
            throw new AccessDeniedException("Order id "+orderId+" does not belong to userId "+customer.getUserId());
        }
        return order;
    }


}
