package com.example.inventoryandorderservice.CustomerPackage.service;

import com.example.inventoryandorderservice.CartPackage.service.CartService;
import com.example.inventoryandorderservice.ProductPackage.service.ProductService;
import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.Cart;
import com.example.inventoryandorderservice.model.CartItem;
import com.example.inventoryandorderservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{
    private ProductService productService;
    private CartService cartService;

    @Autowired
    public CustomerServiceImpl(ProductService productService,CartService cartService)
    {
        this.productService=productService;
        this.cartService=cartService;
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
    public Cart addToCart(long userId, long productId, int quantity) throws ResourceNotFoundException {
       return cartService.addToCart(userId,productId,quantity);
    }

    @Override
    public Cart updateCartItem(long userId, long productId, int quantity) throws ResourceNotFoundException {
        return cartService.updateCartItem(userId,productId,quantity);
    }

    @Override
    public List<CartItem> getCartItems(long userId) throws ResourceNotFoundException {
        return cartService.getCartItems(userId);
    }
}
