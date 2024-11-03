package com.example.inventoryandorderservice.CustomerPackage.controller;

import com.example.inventoryandorderservice.CustomerPackage.dtos.CustomCartItem;
import com.example.inventoryandorderservice.CustomerPackage.dtos.CustomOrder;
import com.example.inventoryandorderservice.CustomerPackage.dtos.CustomOrderDetail;
import com.example.inventoryandorderservice.CustomerPackage.dtos.CustomProduct;
import com.example.inventoryandorderservice.model.CartItem;
import com.example.inventoryandorderservice.model.Order;
import com.example.inventoryandorderservice.model.OrderDetail;
import com.example.inventoryandorderservice.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomResponseFacadeImpl implements CustomResponseFacade{
    @Override
    public List<CustomProduct> getAllCustomProducts(List<Product> products) {
        List<CustomProduct> customProductList=new ArrayList<>();
        for(Product product:products)
        {
            CustomProduct customProduct=new CustomProduct();
            customProduct.setProductId(product.getId());
            customProduct.setProductName(product.getName());
            customProduct.setPrice(product.getPrice());
            customProduct.setDescription(product.getDescription());
            customProduct.setSellerName(product.getSeller().getName());
            customProductList.add(customProduct);
        }
        return customProductList;
    }

    @Override
    public CustomProduct getCustomProduct(Product product) {
        CustomProduct customProduct=new CustomProduct();
        customProduct.setProductId(product.getId());
        customProduct.setProductName(product.getName());
        customProduct.setPrice(product.getPrice());
        customProduct.setDescription(product.getDescription());
        customProduct.setSellerName(product.getSeller().getName());
        return customProduct;
    }

    @Override
    public List<CustomCartItem> getCustomCartItemList(List<CartItem> cartItems) {
        List<CustomCartItem> customCartItems=new ArrayList<>();
        for (CartItem cartItem:cartItems)
        {
            CustomCartItem customCartItem=new CustomCartItem();
            customCartItem.setProductId(cartItem.getProduct().getId());
            customCartItem.setProductName(cartItem.getProduct().getName());
            customCartItem.setPerQuantityPrice(cartItem.getProduct().getPrice());
            customCartItem.setProductTotalPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
            customCartItem.setQuantity(cartItem.getQuantity());
            customCartItems.add(customCartItem);
        }
        return customCartItems;
    }

    @Override
    public List<CustomOrderDetail> getCustomOrderDetailList(List<OrderDetail> orderDetails) {
        List<CustomOrderDetail> customOrderDetails=new ArrayList<>();
        for(OrderDetail orderDetail:orderDetails)
        {
            CustomOrderDetail customOrderDetail=new CustomOrderDetail();
            customOrderDetail.setProductId(orderDetail.getProduct().getId());
            customOrderDetail.setProductName(orderDetail.getProduct().getName());
            customOrderDetail.setQuantity(orderDetail.getQuantity());
            customOrderDetail.setPerQuantityPrice(orderDetail.getProduct().getPrice());
            customOrderDetail.setProductTotalPrice(orderDetail.getQuantity()*orderDetail.getProduct().getPrice());
            customOrderDetails.add(customOrderDetail);
        }
        return customOrderDetails;
    }

    @Override
    public CustomOrder getCustomerOrder(Order order) {
        List<OrderDetail> orderDetails=order.getOrderDetails();
        List<CustomOrderDetail> customOrderDetails=getCustomOrderDetailList(orderDetails);
        CustomOrder customOrder=new CustomOrder();
        customOrder.setOrderId(order.getId());
        customOrder.setUserId(order.getCustomer().getUserId());
        customOrder.setCustomerName(order.getCustomer().getName());
        customOrder.setAddress(order.getDeliveryAddress().toString());
        customOrder.setCustomOrderDetails(customOrderDetails);
        customOrder.setOrderStatus(order.getOrderStatus());
        customOrder.setTotalAmount(order.getTotalAmount());

        return customOrder;
    }

    @Override
    public List<CustomOrder> getCustomOrderList(List<Order> orders) {
        List<CustomOrder> customOrderList=new ArrayList<>();
        for (Order order:orders)
        {
            CustomOrder customOrder=getCustomerOrder(order);
            customOrderList.add(customOrder);
        }
        return customOrderList;
    }

}
