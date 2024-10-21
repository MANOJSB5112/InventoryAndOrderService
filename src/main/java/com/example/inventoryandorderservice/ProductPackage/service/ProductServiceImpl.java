package com.example.inventoryandorderservice.ProductPackage.service;

import com.example.inventoryandorderservice.model.Category;
import com.example.inventoryandorderservice.model.Product;
import com.example.inventoryandorderservice.model.Seller;
import com.example.inventoryandorderservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository)
    {
        this.productRepository=productRepository;
    }
    @Override
    public Product addProduct(Seller seller, Category category, String name, String description, double price) {
        Product product=new Product();
        product.setSeller(seller);
        product.setName(name);
        product.setDescription(description);
        product.setCategory(category);
        product.setPrice(price);
        product=productRepository.save(product);
        return product;
    }
}
