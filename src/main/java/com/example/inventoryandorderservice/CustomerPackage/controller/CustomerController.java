package com.example.inventoryandorderservice.CustomerPackage.controller;

import com.example.inventoryandorderservice.CustomerPackage.service.CustomerService;
import com.example.inventoryandorderservice.dtos.ProductListResponseDto;
import com.example.inventoryandorderservice.dtos.ProductResponseDto;
import com.example.inventoryandorderservice.dtos.ResponseStatus;
import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService)
    {
        this.customerService=customerService;
    }

    @GetMapping("/products")
    public ResponseEntity<ProductListResponseDto> getAllProducts()
    {
        List<Product> products=customerService.getAllProducts();
        ProductListResponseDto productListResponseDto=new ProductListResponseDto();
        productListResponseDto.setProducts(products);
        productListResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(productListResponseDto, HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("productId") Long productId) throws ResourceNotFoundException {
        Product product= customerService.getProductById(productId);
        ProductResponseDto productResponseDto=new ProductResponseDto();
        productResponseDto.setProduct(product);
        productResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(productResponseDto,HttpStatus.OK);
    }
    @GetMapping("/products/all/{categoryId}")
    public ResponseEntity<ProductListResponseDto> getProductByCategoryId(@PathVariable("categoryId") Long categoryId) throws ResourceNotFoundException {
        List<Product> products=customerService.getProductByCategoryId(categoryId);
        ProductListResponseDto productListResponseDto=new ProductListResponseDto();
        productListResponseDto.setProducts(products);
        productListResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(productListResponseDto, HttpStatus.OK);
    }
}
