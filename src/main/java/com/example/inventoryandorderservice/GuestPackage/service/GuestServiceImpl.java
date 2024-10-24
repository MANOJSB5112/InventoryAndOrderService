package com.example.inventoryandorderservice.GuestPackage.service;

import com.example.inventoryandorderservice.ProductPackage.service.ProductService;
import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServiceImpl implements GuestService{
    private ProductService productService;

    @Autowired
    public GuestServiceImpl(ProductService productService)
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
