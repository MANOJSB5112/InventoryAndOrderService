package com.example.inventoryandorderservice.CustomerPackage.controller;

import com.example.inventoryandorderservice.CustomerPackage.dtos.*;
import com.example.inventoryandorderservice.CustomerPackage.service.CustomerService;
import com.example.inventoryandorderservice.dtos.ResponseStatus;
import com.example.inventoryandorderservice.exceptions.*;
import com.example.inventoryandorderservice.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private CustomerService customerService;
    private CustomResponseFacade customResponseFacade;

    public CustomerController(CustomerService customerService,CustomResponseFacade customResponseFacade)
    {
        this.customerService=customerService;
        this.customResponseFacade=customResponseFacade;
    }

    @GetMapping("/products")
    public ResponseEntity<ProductListResponseDto> getAllProducts()
    {
        List<Product> products=customerService.getAllProducts();

        List<CustomProduct> customProducts=customResponseFacade.getAllCustomProducts(products);

        ProductListResponseDto productListResponseDto=new ProductListResponseDto();
        productListResponseDto.setCustomProducts(customProducts);
        productListResponseDto.setResponseStatus(ResponseStatus.SUCCESS);

        return new ResponseEntity<>(productListResponseDto, HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("productId") Long productId) throws ResourceNotFoundException, JsonProcessingException {
        Product product= customerService.getProductById(productId);

        CustomProduct customProduct= customResponseFacade.getCustomProduct(product);

        ProductResponseDto productResponseDto=new ProductResponseDto();
        productResponseDto.setCustomProduct(customProduct);
        productResponseDto.setResponseStatus(ResponseStatus.SUCCESS);

        return new ResponseEntity<>(productResponseDto,HttpStatus.OK);
    }
    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<ProductListResponseDto> getProductByCategoryId(@PathVariable("categoryId") Long categoryId) throws ResourceNotFoundException {
        List<Product> products=customerService.getProductByCategoryId(categoryId);

        List<CustomProduct> customProducts=customResponseFacade.getAllCustomProducts(products);

        ProductListResponseDto productListResponseDto=new ProductListResponseDto();
        productListResponseDto.setCustomProducts(customProducts);
        productListResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(productListResponseDto, HttpStatus.OK);
    }

    @PutMapping("/{userId}/cart/items")
    public ResponseEntity<CreateOrUpdateCartResponseDto> addOrUpdateCartItem(@PathVariable("userId") Long userId,
                                                                        @RequestBody CreateOrUpdateCartRequestDto requestDto)
            throws Exception {
        long productId = requestDto.getProductId();
        int quantity = requestDto.getQuantity();
        Cart cart = customerService.addOrUpdateCartItem(userId, productId, quantity);

        List<CartItem> cartItems=cart.getCartItems();
        List<CustomCartItem> customCartItems=customResponseFacade.getCustomCartItemList(cartItems);

        CreateOrUpdateCartResponseDto responseDto = new CreateOrUpdateCartResponseDto();
        responseDto.setCartId(cart.getId());
        responseDto.setCustomCartItems(customCartItems);
        responseDto.setTotalCartValue(cart.getTotalPrice());
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{userId}/cart/items")
    public ResponseEntity<GetCartItemResponseDto> getCartItems(@PathVariable("userId") Long userId)
            throws ResourceNotFoundException {
        Cart cart = customerService.getCartItems(userId);

        List<CartItem> cartItems=cart.getCartItems();
        List<CustomCartItem> customCartItems=customResponseFacade.getCustomCartItemList(cartItems);

        GetCartItemResponseDto responseDto = new GetCartItemResponseDto();
        responseDto.setCartId(cart.getId());
        responseDto.setCustomCartItems(customCartItems);
        responseDto.setTotalCartValue(cart.getTotalPrice());
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/{userId}/orders/")
    public ResponseEntity<PlaceOrderResponseDto> placeOrder(@PathVariable("userId") Long userId,@RequestBody PlaceOrderRequestDto requestDto) throws AddressNotMatchForUser, OutOfStockException, ResourceNotFoundException, HighDemandProductException {
        long addressId=requestDto.getAddressId();
        Order order=customerService.placeOrder(userId,addressId);

        CustomOrder customOrder=customResponseFacade.getCustomerOrder(order);

        PlaceOrderResponseDto responseDto=new PlaceOrderResponseDto();
        responseDto.setCustomOrder(customOrder);
        responseDto.setStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/orders/")
    public ResponseEntity<GetOrdersListResponseDto> getAllOrders(@PathVariable("userId") Long userId) throws ResourceNotFoundException {
        List<Order> orders= customerService.getAllOrdersForCustomer(userId);

        List<CustomOrder> customOrderList=customResponseFacade.getCustomOrderList(orders);

        GetOrdersListResponseDto responseDto=new GetOrdersListResponseDto();
        responseDto.setCustomOrderList(customOrderList);
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<GetOrderResponseDto> getOrderById(@PathVariable("userId") Long userId,@PathVariable("orderId") Long orderId) throws ResourceNotFoundException, AccessDeniedException {
        Order order = customerService.getOrderByIdForCustomer(userId, orderId);

        CustomOrder customOrder=customResponseFacade.getCustomerOrder(order);

        GetOrderResponseDto responseDto = new GetOrderResponseDto();
        responseDto.setCustomOrder(customOrder);
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

    @PostMapping("/address/{userId}")
    public ResponseEntity<AddAddressResponseDto> addNewAddress(@PathVariable("userId") Long userId,AddAddressRequestDto addAddressRequestDto)
    {
        Address address= customerService.addNewAddress(userId,addAddressRequestDto);

        AddAddressResponseDto responseDto=new AddAddressResponseDto();
        responseDto.setAddress(address.toString());
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }

}
