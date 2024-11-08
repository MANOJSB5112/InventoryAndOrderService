package com.example.inventoryandorderservice.SellerPackage.service;

import com.example.inventoryandorderservice.ProductPackage.service.ProductService;
import com.example.inventoryandorderservice.exceptions.AccessDeniedException;
import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.Product;
import com.example.inventoryandorderservice.model.Seller;
import com.example.inventoryandorderservice.model.UserType;
import com.example.inventoryandorderservice.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService{
    private SellerRepository sellerRepository;
    private ProductService productService;


    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository, ProductService productService)
    {
        this.sellerRepository=sellerRepository;
        this.productService=productService;

    }

    @Override
    public Product addProduct(long sellerId, long categoryId, String name, String description, double price) throws ResourceNotFoundException{
        Seller seller=validateSellerAndGet(sellerId);
        return productService.addProduct(seller,categoryId,name,description,price);
    }

    @Override
    public Product updateProduct(long sellerId, long productId,long  categoryId, String name, String description, double price) throws ResourceNotFoundException, AccessDeniedException {
        Seller seller=validateSellerAndGet(sellerId);
        return productService.updateProduct(seller,productId,categoryId,name,description,price);
    }

    @Override
    public void deleteProduct(long sellerId, long productId) throws ResourceNotFoundException, AccessDeniedException {
        Seller seller=validateSellerAndGet(sellerId);
        productService.deleteProduct(seller,productId);
    }

    @Override
    public void createNewSeller(long userId, String name, String email, String phoneNumber, UserType userType) {
        Seller seller=new Seller();
        seller.setUserId(userId);
        seller.setName(name);
        seller.setEmail(email);
        seller.setPhoneNumber(phoneNumber);
        seller.setUserType(userType);
        sellerRepository.save(seller);
    }

    public Seller validateSellerAndGet(Long sellerId) throws ResourceNotFoundException {
        Optional<Seller> sellerOptional =sellerRepository.findByUserId(sellerId);
        if(sellerOptional.isEmpty())
        {
            throw new ResourceNotFoundException("Seller not found with the give id "+sellerId);
        }
        return sellerOptional.get();
    }


}
