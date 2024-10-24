package com.example.inventoryandorderservice.ProductPackage.service;

import com.example.inventoryandorderservice.CategoryPackage.service.CategoryService;
import com.example.inventoryandorderservice.exceptions.AccessDeniedException;
import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.Category;
import com.example.inventoryandorderservice.model.Product;
import com.example.inventoryandorderservice.model.Seller;
import com.example.inventoryandorderservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,CategoryService categoryService)
    {
        this.productRepository=productRepository;
        this.categoryService=categoryService;
    }
    @Override
    public Product addProduct(Seller seller, long categoryId, String name, String description, double price) throws ResourceNotFoundException {
        Category category=categoryService.validateCategoryAndGet(categoryId);
        Product product=new Product();
        product.setSeller(seller);
        product.setName(name);
        product.setDescription(description);
        product.setCategory(category);
        product.setPrice(price);
        product=productRepository.save(product);
        return product;
    }

    @Override
    public Product updateProduct(Seller seller, Long productId, Long categoryId, String name, String description, Double price) throws ResourceNotFoundException, AccessDeniedException {
        Product product=validateSellerForProductIdAndGet(seller,productId);
        if(categoryId!=null)
        {
            Category category= categoryService.validateCategoryAndGet(categoryId);
            product.setCategory(category);
        }
        if(name!=null)
        {
            product.setName(name);
        }
        if(description!=null)
        {
            product.setDescription(description);
        }
        if(price!=null) {
            product.setPrice(price);
        }
        productRepository.save(product);
        return product;
    }

    @Override
    public void deleteProduct(Seller seller, long productId) throws AccessDeniedException, ResourceNotFoundException {
        Product product=validateSellerForProductIdAndGet(seller,productId);
        product.setIsDeleted(true);
        productRepository.save(product);
    }

    public Product validateSellerForProductIdAndGet(Seller seller, long productId) throws ResourceNotFoundException, AccessDeniedException {
        Optional<Product> productOptional=productRepository.findById(productId);
        if(productOptional.isEmpty())
        {
            throw new ResourceNotFoundException("Product not found with the id : "+productId);
        }
        Product product=productOptional.get();
        if(!Objects.equals(product.getSeller().getId(), seller.getId()))
        {
            throw new AccessDeniedException("Product id "+productId+" does not belong to the Seller id "+seller.getId());
        }
        return product;
    }
}
