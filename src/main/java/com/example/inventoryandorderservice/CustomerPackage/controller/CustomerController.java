package com.example.inventoryandorderservice.CustomerPackage.controller;

import com.example.inventoryandorderservice.CustomerPackage.dtos.*;
import com.example.inventoryandorderservice.CustomerPackage.service.CustomerService;
import com.example.inventoryandorderservice.dtos.ResponseStatus;
import com.example.inventoryandorderservice.exceptions.AddressNotMatchForUser;
import com.example.inventoryandorderservice.exceptions.HighDemandProductException;
import com.example.inventoryandorderservice.exceptions.OutOfStockException;
import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.Cart;
import com.example.inventoryandorderservice.model.CartItem;
import com.example.inventoryandorderservice.model.Order;
import com.example.inventoryandorderservice.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
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

    @PostMapping("/cart/items")
    public ResponseEntity<CreateOrUpdateCartResponseDto> addToCart(@RequestBody CreateOrUpdateCartRequestDto requestDto) throws ResourceNotFoundException {
        long userId=requestDto.getUserId();
        long productId=requestDto.getProductId();
        int quantity=requestDto.getQuantity();
        Cart cart = customerService.addToCart(userId, productId, quantity);
        CreateOrUpdateCartResponseDto responseDto=new CreateOrUpdateCartResponseDto();
        responseDto.setCart(cart);
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/cart/items")
    public ResponseEntity<CreateOrUpdateCartResponseDto> updateCartItem(@RequestBody CreateOrUpdateCartRequestDto requestDto) throws ResourceNotFoundException, OutOfStockException, HighDemandProductException {
        long userId=requestDto.getUserId();
        long productId=requestDto.getProductId();
        int quantity=requestDto.getQuantity();
        Cart cart = customerService.updateCartItem(userId, productId, quantity);
        CreateOrUpdateCartResponseDto responseDto=new CreateOrUpdateCartResponseDto();
        responseDto.setCart(cart);
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/cart/items")
    public ResponseEntity<GetCartItemResponseDto> getCartItems(@RequestBody GetCartItemsRequestDto getCartItemsRequestDto) throws ResourceNotFoundException {
        long userId=getCartItemsRequestDto.getUserId();
        List<CartItem> cartItems = customerService.getCartItems(userId);
        GetCartItemResponseDto responseDto=new GetCartItemResponseDto();
        responseDto.setCartItems(cartItems);
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<PlaceOrderResponseDto> placeOrder(@RequestBody PlaceOrderRequestDto requestDto) throws AddressNotMatchForUser, OutOfStockException, ResourceNotFoundException, HighDemandProductException {
        long userId=requestDto.getUserId();
        long addressId=requestDto.getAddressId();
        Order order=customerService.placeOrder(userId,addressId);
        PlaceOrderResponseDto responseDto=new PlaceOrderResponseDto();
        responseDto.setOrder(order);
        responseDto.setStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }
}
