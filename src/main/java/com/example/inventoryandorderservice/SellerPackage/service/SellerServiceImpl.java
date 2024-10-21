package com.example.inventoryandorderservice.SellerPackage.service;

import com.example.inventoryandorderservice.CategoryPackage.exceptions.CategoryNotFoundException;
import com.example.inventoryandorderservice.CategoryPackage.service.CategoryService;
import com.example.inventoryandorderservice.ProductPackage.service.ProductService;
import com.example.inventoryandorderservice.dtos.UserNotFoundException;
import com.example.inventoryandorderservice.model.Category;
import com.example.inventoryandorderservice.model.Product;
import com.example.inventoryandorderservice.model.Seller;
import com.example.inventoryandorderservice.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService{
    private SellerRepository sellerRepository;
    private ProductService productService;
    private CategoryService categoryService;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository, ProductService productService, CategoryService categoryService)
    {
        this.sellerRepository=sellerRepository;
        this.productService=productService;
        this.categoryService=categoryService;
    }

    @Override
    public Product addProduct(long userId, long categoryId, String name, String description, double price) throws UserNotFoundException, CategoryNotFoundException {
        Seller seller=validateSellerAndGet(userId);
        Category category=categoryService.validateCategoryAndGet(categoryId);
        return productService.addProduct(seller,category,name,description,price);
    }

    public Seller validateSellerAndGet(Long sellerId) throws UserNotFoundException {
        Optional<Seller> sellerOptional =sellerRepository.findById(sellerId);
        if(sellerOptional.isEmpty())
        {
            throw new UserNotFoundException("User not found with the give id");
        }
        return sellerOptional.get();
    }


}
