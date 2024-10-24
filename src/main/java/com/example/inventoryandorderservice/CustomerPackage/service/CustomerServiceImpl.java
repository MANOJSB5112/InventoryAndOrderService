package com.example.inventoryandorderservice.CustomerPackage.service;

import com.example.inventoryandorderservice.ProductPackage.service.ProductService;
import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{
    private ProductService productService;

    @Autowired
    public CustomerServiceImpl(ProductService productService)
    {
        this.productService=productService;
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
}
