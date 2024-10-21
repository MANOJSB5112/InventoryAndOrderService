package com.example.inventoryandorderservice.SellerPackage.service;

import com.example.inventoryandorderservice.SellerPackage.exceptions.CategoryNotFoundException;
import com.example.inventoryandorderservice.SellerPackage.exceptions.UserNotFoundException;
import com.example.inventoryandorderservice.model.Category;
import com.example.inventoryandorderservice.model.Product;
import com.example.inventoryandorderservice.model.Seller;
import com.example.inventoryandorderservice.repository.CategoryRepository;
import com.example.inventoryandorderservice.repository.ProductRepository;
import com.example.inventoryandorderservice.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService{
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private SellerRepository sellerRepository;

    @Autowired
    public SellerServiceImpl(ProductRepository productRepository,CategoryRepository categoryRepository,
                             SellerRepository sellerRepository)
    {
        this.productRepository=productRepository;
        this.categoryRepository=categoryRepository;
        this.sellerRepository=sellerRepository;
    }

    @Override
    public Product addProduct(long userId, long categoryId, String name, String description, double price) throws UserNotFoundException, CategoryNotFoundException {
        Seller seller=validateSellerAndGet(userId);
        Category category=validateCategoryAndGet(categoryId);

        Product product=new Product();
        product.setSeller(seller);
        product.setName(name);
        product.setDescription(description);
        product.setCategory(category);
        product.setPrice(price);
        product=productRepository.save(product);

        return product;
    }

    public Seller validateSellerAndGet(Long sellerId) throws UserNotFoundException {
        Optional<Seller> sellerOptional =sellerRepository.findById(sellerId);
        if(sellerOptional.isEmpty())
        {
            throw new UserNotFoundException("User not found with the give id");
        }
        Seller seller=sellerOptional.get();
        return seller;
    }

    public Category validateCategoryAndGet(Long categoryId) throws CategoryNotFoundException {
        Optional<Category> categoryOptional=categoryRepository.findById(categoryId);
        if(categoryOptional.isEmpty())
        {
            throw new CategoryNotFoundException("Category Not found ");
        }
        Category category=categoryOptional.get();
        return category;
    }
}
