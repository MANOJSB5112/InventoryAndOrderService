package com.example.inventoryandorderservice.SellerPackage.controller;

import com.example.inventoryandorderservice.CategoryPackage.exceptions.CategoryNotFoundException;
import com.example.inventoryandorderservice.SellerPackage.dtos.CreateOrUpdateProductRequestDto;
import com.example.inventoryandorderservice.SellerPackage.dtos.CreateOrUpdateProductResponseDto;
import com.example.inventoryandorderservice.SellerPackage.service.SellerService;
import com.example.inventoryandorderservice.dtos.ResponseStatus;
import com.example.inventoryandorderservice.dtos.UserNotFoundException;
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
    public ResponseEntity<CreateOrUpdateProductResponseDto> addProduct(@PathVariable long sellerId, @RequestBody CreateOrUpdateProductRequestDto createOrUpdateProductRequestDto) throws UserNotFoundException, CategoryNotFoundException {
        long categoryId=createOrUpdateProductRequestDto.getCategoryId();
        String productName = createOrUpdateProductRequestDto.getName();
        String productDescription = createOrUpdateProductRequestDto.getDescription();
        double price = createOrUpdateProductRequestDto.getPrice();

        Product product = sellerService.addProduct(sellerId,categoryId, productName, productDescription, price);

        CreateOrUpdateProductResponseDto createProductResponseDto = new CreateOrUpdateProductResponseDto();
        createProductResponseDto.setProduct(product);
        createProductResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(createProductResponseDto, HttpStatus.CREATED);
    }




}
