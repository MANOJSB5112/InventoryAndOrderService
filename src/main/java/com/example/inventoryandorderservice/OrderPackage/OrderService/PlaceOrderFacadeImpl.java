package com.example.inventoryandorderservice.OrderPackage.OrderService;

import com.example.inventoryandorderservice.AddressPackage.Service.AddressService;
import com.example.inventoryandorderservice.CartPackage.service.CartService;
import com.example.inventoryandorderservice.exceptions.AddressNotMatchForUser;
import com.example.inventoryandorderservice.exceptions.OutOfStockException;
import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.*;
import com.example.inventoryandorderservice.repository.OrderDetailRepository;
import com.example.inventoryandorderservice.repository.OrderRepository;
import com.example.inventoryandorderservice.repository.ProductInventoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PlaceOrderFacadeImpl implements PlaceOrderFacade{
    private AddressService addressService;
    private CartService cartService;
    private ProductInventoryRepository productInventoryRepository;
    private OrderDetailRepository orderDetailRepository;
    private OrderRepository orderRepository;


    @Autowired
    public PlaceOrderFacadeImpl(AddressService addressService,CartService cartService,
                            ProductInventoryRepository productInventoryRepository,OrderDetailRepository orderDetailRepository,
                            OrderRepository orderRepository)
    {
        this.addressService=addressService;
        this.cartService=cartService;
        this.productInventoryRepository=productInventoryRepository;
        this.orderDetailRepository=orderDetailRepository;
        this.orderRepository=orderRepository;
    }
    @Override
    @Transactional
    public Order placeOrder(Customer customer, long addressId) throws AddressNotMatchForUser, ResourceNotFoundException, OutOfStockException {
        Address address=addressService.validateAddressForUserAndGet(customer.getUserId(),addressId);
        Cart cart=cartService.getCartItems(customer.getUserId());
        List<CartItem> cartItems=cart.getCartItems();
        double totalAmount=cartService.getCartValue(customer.getUserId());
        List<OrderDetail> orderDetails=getOrderDetailsForCartItems(cartItems);
        return getOrderForOrderDetails(orderDetails,customer,address,totalAmount);
    }

    public List<OrderDetail> getOrderDetailsForCartItems( List<CartItem> cartItems) throws OutOfStockException, ResourceNotFoundException {
        List<OrderDetail> newOrderDetails=new ArrayList<>();
        for(CartItem cartItem:cartItems)
        {
            Product product=cartItem.getProduct();
            int orderedQuantity=cartItem.getQuantity();

            Optional<ProductInventory> inventoryOptional = productInventoryRepository.findByProductId(product.getId());
            ProductInventory inventory = inventoryOptional
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found in Inventory"));

            int hasQuantity = inventory.getQuantity();
            if(hasQuantity<orderedQuantity)
            {
                throw new OutOfStockException("Ops ! The "+product.getName()+" has only "+hasQuantity+" quantity");
            }
            inventory.setQuantity(hasQuantity - orderedQuantity);
            productInventoryRepository.save(inventory);

            OrderDetail newOrderDetail=new OrderDetail();
            newOrderDetail.setProduct(product);
            newOrderDetail.setQuantity(orderedQuantity);
            newOrderDetail = orderDetailRepository.save(newOrderDetail);
            newOrderDetails.add(newOrderDetail);
        }
        return newOrderDetails;
    }

    public Order getOrderForOrderDetails(List<OrderDetail> orderDetails,Customer customer,Address address,double totalAmount)
    {
        Order order = new Order();

        order.setTotalAmount(totalAmount);
        order.setCustomer(customer);
        order.setDeliveryAddress(address);
        order.setOrderStatus(OrderStatus.PLACED);
        order.setOrderDetails(orderDetails);

        order = orderRepository.save(order);

        for (OrderDetail newOrderDetail : orderDetails) {
            newOrderDetail.setOrder(order);
            orderDetailRepository.save(newOrderDetail);
        }

        return order;
    }
}
