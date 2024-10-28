package com.example.inventoryandorderservice.CustomerPackage.controller;

import com.example.inventoryandorderservice.CustomerPackage.dtos.*;
import com.example.inventoryandorderservice.CustomerPackage.service.CustomerService;
import com.example.inventoryandorderservice.dtos.ResponseStatus;
import com.example.inventoryandorderservice.exceptions.*;
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
    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<ProductListResponseDto> getProductByCategoryId(@PathVariable("categoryId") Long categoryId) throws ResourceNotFoundException {
        List<Product> products=customerService.getProductByCategoryId(categoryId);
        ProductListResponseDto productListResponseDto=new ProductListResponseDto();
        productListResponseDto.setProducts(products);
        productListResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(productListResponseDto, HttpStatus.OK);
    }

    @PostMapping("/{userId}/cart/items")
    public ResponseEntity<CreateOrUpdateCartResponseDto> addToCart(@PathVariable("userId") Long userId,
                                                                   @RequestBody CreateOrUpdateCartRequestDto requestDto) throws ResourceNotFoundException {
        long productId = requestDto.getProductId();
        int quantity = requestDto.getQuantity();

        Cart cart = customerService.addToCart(userId, productId, quantity);
        CreateOrUpdateCartResponseDto responseDto = new CreateOrUpdateCartResponseDto();
        responseDto.setCart(cart);
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}/cart/items")
    public ResponseEntity<CreateOrUpdateCartResponseDto> updateCartItem(@PathVariable("userId") Long userId,
                                                                        @RequestBody CreateOrUpdateCartRequestDto requestDto)
            throws ResourceNotFoundException, OutOfStockException, HighDemandProductException {
        long productId = requestDto.getProductId();
        int quantity = requestDto.getQuantity();

        Cart cart = customerService.updateCartItem(userId, productId, quantity);
        CreateOrUpdateCartResponseDto responseDto = new CreateOrUpdateCartResponseDto();
        responseDto.setCart(cart);
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{userId}/cart/items")
    public ResponseEntity<GetCartItemResponseDto> getCartItems(@PathVariable("userId") Long userId)
            throws ResourceNotFoundException {
        List<CartItem> cartItems = customerService.getCartItems(userId);
        GetCartItemResponseDto responseDto = new GetCartItemResponseDto();
        responseDto.setCartItems(cartItems);
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/{userId}/orders/")
    public ResponseEntity<PlaceOrderResponseDto> placeOrder(@PathVariable("userId") Long userId,@RequestBody PlaceOrderRequestDto requestDto) throws AddressNotMatchForUser, OutOfStockException, ResourceNotFoundException, HighDemandProductException {
        long addressId=requestDto.getAddressId();
        Order order=customerService.placeOrder(userId,addressId);
        PlaceOrderResponseDto responseDto=new PlaceOrderResponseDto();
        responseDto.setOrder(order);
        responseDto.setStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/orders/")
    public ResponseEntity<GetOrdersListResponseDto> getAllOrders(@PathVariable("userId") Long userId) throws ResourceNotFoundException {
        List<Order> orders= customerService.getAllOrdersForCustomer(userId);

        GetOrdersListResponseDto responseDto=new GetOrdersListResponseDto();
        responseDto.setOrders(orders);
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<GetOrderResponseDto> getOrderById(@PathVariable("userId") Long userId,@PathVariable("orderId") Long orderId) throws ResourceNotFoundException, AccessDeniedException {
        Order order = customerService.getOrderByIdForCustomer(userId, orderId);

        GetOrderResponseDto responseDto = new GetOrderResponseDto();
        responseDto.setOrder(order);
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<CancelOrderResponseDto> cancelOrderById(@PathVariable("userId") Long userId,@PathVariable("orderId") Long orderId) throws ResourceNotFoundException, AccessDeniedException {
        String message=customerService.cancelOrderById(userId,orderId);
        CancelOrderResponseDto responseDto=new CancelOrderResponseDto();
        responseDto.setMessage(message);
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(responseDto);
    }
}
