package com.example.inventoryandorderservice.SellerPackage.controller;

import com.example.inventoryandorderservice.SellerPackage.dtos.CreateProductRequestDto;
import com.example.inventoryandorderservice.SellerPackage.dtos.CreateProductResponseDto;
import com.example.inventoryandorderservice.SellerPackage.exceptions.CategoryNotFoundException;
import com.example.inventoryandorderservice.SellerPackage.exceptions.UserNotFoundException;
import com.example.inventoryandorderservice.SellerPackage.service.SellerService;
import com.example.inventoryandorderservice.dtos.ResponseStatus;
import com.example.inventoryandorderservice.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    private SellerService sellerService;

    public SellerController(SellerService sellerService)
    {
        this.sellerService=sellerService;
    }
    @PostMapping("/{sellerId}/products")
    public ResponseEntity<CreateProductResponseDto> addProduct(@PathVariable long sellerId, @RequestBody CreateProductRequestDto createProductRequestDto) throws UserNotFoundException, CategoryNotFoundException {
        long userId = sellerId;
        long categoryId=createProductRequestDto.getCategoryId();
        String productName = createProductRequestDto.getName();
        String productDescription = createProductRequestDto.getDescription();
        double price = createProductRequestDto.getPrice();

        Product product = sellerService.addProduct(userId,categoryId, productName, productDescription, price);

        CreateProductResponseDto createProductResponseDto = new CreateProductResponseDto();
        createProductResponseDto.setProduct(product);
        createProductResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(createProductResponseDto, HttpStatus.CREATED);
    }




}
