package com.example.inventoryandorderservice.CustomerPackage.controller;

import com.example.inventoryandorderservice.CustomerPackage.dtos.*;
import com.example.inventoryandorderservice.CustomerPackage.service.CustomerService;
import com.example.inventoryandorderservice.dtos.ResponseStatus;
import com.example.inventoryandorderservice.exceptions.*;
import com.example.inventoryandorderservice.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        List<CustomProduct> customProducts=new ArrayList<>();
        for(Product product:products)
        {
            CustomProduct customProduct=new CustomProduct();
            customProduct.setProductId(product.getId());
            customProduct.setProductName(product.getName());
            customProduct.setPrice(product.getPrice());
            customProduct.setDescription(product.getDescription());
            customProduct.setSellerName(product.getSeller().getName());
            customProducts.add(customProduct);
        }
        ProductListResponseDto productListResponseDto=new ProductListResponseDto();
        productListResponseDto.setCustomProducts(customProducts);
        productListResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(productListResponseDto, HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("productId") Long productId) throws ResourceNotFoundException {
        Product product= customerService.getProductById(productId);

        CustomProduct customProduct=new CustomProduct();
        customProduct.setProductId(product.getId());
        customProduct.setProductName(product.getName());
        customProduct.setPrice(product.getPrice());
        customProduct.setDescription(product.getDescription());
        customProduct.setSellerName(product.getSeller().getName());

        ProductResponseDto productResponseDto=new ProductResponseDto();
        productResponseDto.setCustomProduct(customProduct);
        productResponseDto.setResponseStatus(ResponseStatus.SUCCESS);

        return new ResponseEntity<>(productResponseDto,HttpStatus.OK);
    }
    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<ProductListResponseDto> getProductByCategoryId(@PathVariable("categoryId") Long categoryId) throws ResourceNotFoundException {
        List<Product> products=customerService.getProductByCategoryId(categoryId);
        List<CustomProduct> customProducts=new ArrayList<>();
        for(Product product:products)
        {
            CustomProduct customProduct=new CustomProduct();
            customProduct.setProductId(product.getId());
            customProduct.setProductName(product.getName());
            customProduct.setPrice(product.getPrice());
            customProduct.setDescription(product.getDescription());
            customProduct.setSellerName(product.getSeller().getName());
            customProducts.add(customProduct);
        }
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
        List<CustomCartItem> customCartItems=new ArrayList<>();
        for (CartItem cartItem:cartItems)
        {
            CustomCartItem customCartItem=new CustomCartItem();
            customCartItem.setProductId(cartItem.getProduct().getId());
            customCartItem.setProductName(cartItem.getProduct().getName());
            customCartItem.setPerQuantityPrice(cartItem.getProduct().getPrice());
            customCartItem.setProductTotalPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
            customCartItem.setQuantity(cartItem.getQuantity());
            customCartItems.add(customCartItem);
        }
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
        List<CustomCartItem> customCartItems=new ArrayList<>();
        for (CartItem cartItem:cartItems)
        {
            CustomCartItem customCartItem=new CustomCartItem();
            customCartItem.setProductId(cartItem.getProduct().getId());
            customCartItem.setProductName(cartItem.getProduct().getName());
            customCartItem.setPerQuantityPrice(cartItem.getProduct().getPrice());
            customCartItem.setQuantity(cartItem.getQuantity());
            customCartItem.setProductTotalPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
            customCartItems.add(customCartItem);
        }

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

        List<OrderDetail> orderDetails=order.getOrderDetails();
        List<CustomOrderDetail> customOrderDetails=new ArrayList<>();
        for(OrderDetail orderDetail:orderDetails)
        {
            CustomOrderDetail customOrderDetail=new CustomOrderDetail();
            customOrderDetail.setProductId(orderDetail.getProduct().getId());
            customOrderDetail.setProductName(orderDetail.getProduct().getName());
            customOrderDetail.setQuantity(orderDetail.getQuantity());
            customOrderDetail.setPerQuantityPrice(orderDetail.getProduct().getPrice());
            customOrderDetail.setProductTotalPrice(orderDetail.getQuantity()*orderDetail.getProduct().getPrice());
            customOrderDetails.add(customOrderDetail);
        }
        CustomOrder customOrder=new CustomOrder();
        customOrder.setOrderId(order.getId());
        customOrder.setUserId(order.getCustomer().getUserId());
        customOrder.setCustomerName(order.getCustomer().getName());
        customOrder.setAddress(order.getDeliveryAddress().toString());
        customOrder.setCustomOrderDetails(customOrderDetails);
        customOrder.setOrderStatus(order.getOrderStatus());
        customOrder.setTotalAmount(order.getTotalAmount());

        PlaceOrderResponseDto responseDto=new PlaceOrderResponseDto();
        responseDto.setCustomOrder(customOrder);
        responseDto.setStatus(ResponseStatus.SUCCESS);
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/orders/")
    public ResponseEntity<GetOrdersListResponseDto> getAllOrders(@PathVariable("userId") Long userId) throws ResourceNotFoundException {
        List<Order> orders= customerService.getAllOrdersForCustomer(userId);
        List<CustomOrder> customOrderList=new ArrayList<>();
        for (Order order:orders)
        {
            List<OrderDetail> orderDetails=order.getOrderDetails();
            List<CustomOrderDetail> customOrderDetails=new ArrayList<>();
            for(OrderDetail orderDetail:orderDetails)
            {
                CustomOrderDetail customOrderDetail=new CustomOrderDetail();
                customOrderDetail.setProductId(orderDetail.getProduct().getId());
                customOrderDetail.setProductName(orderDetail.getProduct().getName());
                customOrderDetail.setQuantity(orderDetail.getQuantity());
                customOrderDetail.setPerQuantityPrice(orderDetail.getProduct().getPrice());
                customOrderDetail.setProductTotalPrice(orderDetail.getQuantity()*orderDetail.getProduct().getPrice());
                customOrderDetails.add(customOrderDetail);
            }
            CustomOrder customOrder=new CustomOrder();
            customOrder.setOrderId(order.getId());
            customOrder.setUserId(order.getCustomer().getUserId());
            customOrder.setCustomerName(order.getCustomer().getName());
            customOrder.setAddress(order.getDeliveryAddress().toString());
            customOrder.setCustomOrderDetails(customOrderDetails);
            customOrder.setOrderStatus(order.getOrderStatus());
            customOrder.setTotalAmount(order.getTotalAmount());
            customOrderList.add(customOrder);
        }
        GetOrdersListResponseDto responseDto=new GetOrdersListResponseDto();
        responseDto.setCustomOrderList(customOrderList);
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<GetOrderResponseDto> getOrderById(@PathVariable("userId") Long userId,@PathVariable("orderId") Long orderId) throws ResourceNotFoundException, AccessDeniedException {
        Order order = customerService.getOrderByIdForCustomer(userId, orderId);

        List<OrderDetail> orderDetails=order.getOrderDetails();
        List<CustomOrderDetail> customOrderDetails=new ArrayList<>();
        for(OrderDetail orderDetail:orderDetails)
        {
            CustomOrderDetail customOrderDetail=new CustomOrderDetail();
            customOrderDetail.setProductId(orderDetail.getProduct().getId());
            customOrderDetail.setProductName(orderDetail.getProduct().getName());
            customOrderDetail.setQuantity(orderDetail.getQuantity());
            customOrderDetail.setPerQuantityPrice(orderDetail.getProduct().getPrice());
            customOrderDetail.setProductTotalPrice(orderDetail.getQuantity()*orderDetail.getProduct().getPrice());
            customOrderDetails.add(customOrderDetail);
        }
        CustomOrder customOrder=new CustomOrder();
        customOrder.setOrderId(order.getId());
        customOrder.setUserId(order.getCustomer().getUserId());
        customOrder.setCustomerName(order.getCustomer().getName());
        customOrder.setAddress(order.getDeliveryAddress().toString());
        customOrder.setCustomOrderDetails(customOrderDetails);
        customOrder.setOrderStatus(order.getOrderStatus());
        customOrder.setTotalAmount(order.getTotalAmount());

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
