package com.example.inventoryandorderservice.CustomerPackage.service;


import com.example.inventoryandorderservice.CartPackage.service.CartService;
import com.example.inventoryandorderservice.OrderPackage.OrderService.OrderService;
import com.example.inventoryandorderservice.ProductPackage.service.ProductService;
import com.example.inventoryandorderservice.exceptions.*;
import com.example.inventoryandorderservice.model.*;
import com.example.inventoryandorderservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{
    private ProductService productService;
    private CartService cartService;
    private OrderService orderService;
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(ProductService productService,CartService cartService,OrderService orderService,
                               CustomerRepository customerRepository)
    {
        this.productService=productService;
        this.cartService=cartService;
        this.orderService=orderService;
        this.customerRepository=customerRepository;
    }
    @Override
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @Override
    public Product getProductById(Long productId) throws ResourceNotFoundException {
        return productService.getProductById(productId);
    }

    @Override
    public List<Product> getProductByCategoryId(Long categoryId) throws ResourceNotFoundException {
        return productService.getProductByCategoryId(categoryId);
    }

    @Override
    public Cart addOrUpdateCartItem(long userId, long productId, int quantity) throws Exception {
        Customer customer=validateAndGetCustomer(userId);
        return cartService.addOrUpdateCartItem(userId,productId,quantity);
    }

    @Override
    public List<CartItem> getCartItems(long userId) throws ResourceNotFoundException {
        Customer customer=validateAndGetCustomer(userId);
        return cartService.getCartItems(userId);
    }

    @Override
    public Order placeOrder(long userId, long addressId) throws ResourceNotFoundException, AddressNotMatchForUser, OutOfStockException, HighDemandProductException {
        Customer customer=validateAndGetCustomer(userId);
        return orderService.placeOrder(customer,addressId);
    }

    @Override
    public Customer validateAndGetCustomer(long userId) throws ResourceNotFoundException {
        Optional<Customer> customerOptional=customerRepository.findByUserId(userId);
        if(customerOptional.isEmpty())
        {
            throw new ResourceNotFoundException("Customer Not found with the id "+userId);
        }
        return customerOptional.get();
    }

    @Override
    public List<Order> getAllOrdersForCustomer(long userId) throws ResourceNotFoundException {
        Customer customer=validateAndGetCustomer(userId);
        return orderService.getAllOrdersForCustomer(customer);
    }

    @Override
    public Order getOrderByIdForCustomer(long userId, long orderId) throws ResourceNotFoundException, AccessDeniedException {
        Customer customer=validateAndGetCustomer(userId);
        return orderService.getOrderByIdForCustomer(customer,orderId);
    }

    @Override
    public String cancelOrderById(long userId, long orderId) throws ResourceNotFoundException, AccessDeniedException {
        Customer customer=validateAndGetCustomer(userId);
        return orderService.cancelOrderById(customer,orderId);
    }

    @Override
    public void createNewCustomer(long userId, String name, String email, String phoneNumber, UserType userType) {
        Customer customer=new Customer();
        customer.setUserId(userId);
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customer.setUserType(userType);
        customerRepository.save(customer);
    }
}
