package com.example.inventoryandorderservice.SellerPackage.controller;

import com.example.inventoryandorderservice.SellerPackage.dtos.CreateOrUpdateProductRequestDto;
import com.example.inventoryandorderservice.SellerPackage.dtos.CreateOrUpdateProductResponseDto;
import com.example.inventoryandorderservice.SellerPackage.dtos.DeleteProductResponseDto;
import com.example.inventoryandorderservice.SellerPackage.service.SellerService;
import com.example.inventoryandorderservice.dtos.ResponseStatus;
import com.example.inventoryandorderservice.exceptions.AccessDeniedException;
import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    private SellerService sellerService;

    public SellerController(SellerService sellerService)
    {
        this.sellerService=sellerService;
    }
    @PostMapping("/{sellerId}/products")
    public ResponseEntity<CreateOrUpdateProductResponseDto> addProduct(@PathVariable("sellerId") long sellerId, @RequestBody CreateOrUpdateProductRequestDto createOrUpdateProductRequestDto) throws ResourceNotFoundException{
        long categoryId=createOrUpdateProductRequestDto.getCategoryId();
        String productName = createOrUpdateProductRequestDto.getName();
        String productDescription = createOrUpdateProductRequestDto.getDescription();
        double price = createOrUpdateProductRequestDto.getPrice();

        Product product = sellerService.addProduct(sellerId,categoryId, productName, productDescription, price);

        CreateOrUpdateProductResponseDto createOrUpdateProductResponseDto = new CreateOrUpdateProductResponseDto();
        createOrUpdateProductResponseDto.setProduct(product);
        createOrUpdateProductResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(createOrUpdateProductResponseDto, HttpStatus.CREATED);
    }
    @PutMapping("/{sellerId}/products/{productId}")
    public ResponseEntity<CreateOrUpdateProductResponseDto> updateProduct(@PathVariable("sellerId") long sellerId,
                                                                          @RequestBody CreateOrUpdateProductRequestDto createOrUpdateProductRequestDto,
                                                                          @PathVariable("productId") long productId) throws ResourceNotFoundException, AccessDeniedException{
        long categoryId=createOrUpdateProductRequestDto.getCategoryId();
        String productName = createOrUpdateProductRequestDto.getName();
        String productDescription = createOrUpdateProductRequestDto.getDescription();
        double price = createOrUpdateProductRequestDto.getPrice();

        Product product= sellerService.updateProduct(sellerId,productId,categoryId,productName,productDescription,price);
        CreateOrUpdateProductResponseDto createOrUpdateProductResponseDto=new CreateOrUpdateProductResponseDto();
        createOrUpdateProductResponseDto.setProduct(product);
        createOrUpdateProductResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(createOrUpdateProductResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{sellerId}/products/{productId}")
    public ResponseEntity<DeleteProductResponseDto> deleteProduct(@PathVariable("sellerId") long sellerId, @PathVariable("productId") long productId) throws AccessDeniedException, ResourceNotFoundException {
        sellerService.deleteProduct(sellerId,productId);

        DeleteProductResponseDto responseDto=new DeleteProductResponseDto();
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }


}
