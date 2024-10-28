package com.example.inventoryandorderservice.CartPackage.service;

import com.example.inventoryandorderservice.exceptions.HighDemandProductException;
import com.example.inventoryandorderservice.exceptions.OutOfStockException;
import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.*;
import com.example.inventoryandorderservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private CartItemRepository cartItemRepository;

    private ProductInventoryRepository productInventoryRepository;
    private HighDemandProductRepository highDemandProductRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository,
                           ProductInventoryRepository productInventoryRepository,HighDemandProductRepository highDemandProductRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.productInventoryRepository = productInventoryRepository;
        this.highDemandProductRepository=highDemandProductRepository;
    }

    @Override
    public Cart addToCart(long userId, long productId, int quantity) throws ResourceNotFoundException {
        Cart cart = cartRepository.findByCustomerId(userId).orElse(new Cart());
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        CartItem cartItem = null;
        if (existingItem.isPresent()) {
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);
            cart.getCartItems().add(cartItem);
        }
        cartItemRepository.save(cartItem);
        cart.setTotalPrice(calculateTotalPrice(cart));
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateCartItem(long userId, long productId, int quantity) throws ResourceNotFoundException, OutOfStockException, HighDemandProductException {
        Cart cart = cartRepository.findByCustomerId(userId).orElseThrow(() -> new ResourceNotFoundException("Cart not found with the userId " + userId));
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        if (existingItem.isEmpty()) {
            throw new ResourceNotFoundException("CartItem not found with productId " + productId);
        }
        CartItem cartItem = existingItem.get();
        if (quantity == 0) {
            deleteCartItem(cart, cartItem);
        } else {
            int orderedQuantity=verifyMaxPossibleQuantity(productId,quantity);
            cartItem.setQuantity(orderedQuantity);
            cartItemRepository.save(cartItem);
            cart.setTotalPrice(calculateTotalPrice(cart));
            cartRepository.save(cart);
        }
        return cart;
    }

    @Override
    public List<CartItem> getCartItems(long userId) throws ResourceNotFoundException {
        Cart cart = cartRepository.findByCustomerId(userId).orElseThrow(() -> new ResourceNotFoundException("Cart not found for Customer with Id " + userId));
        return cartItemRepository.findAllByCartId(cart.getId());
    }

    @Override
    public Double getCartValue(long userId) throws ResourceNotFoundException {
        Cart cart = cartRepository.findByCustomerId(userId).orElseThrow(() -> new ResourceNotFoundException("Cart not found for Customer with Id " + userId));
        return cart.getTotalPrice();
    }

    public void deleteCartItem(Cart cart, CartItem cartItem) {
        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        cart.setTotalPrice(calculateTotalPrice(cart));
        cartRepository.save(cart);
    }

    private Double calculateTotalPrice(Cart cart) {
        return cart.getCartItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    public int verifyMaxPossibleQuantity(long productId, int orderedQuantity) throws ResourceNotFoundException, HighDemandProductException, OutOfStockException {
        Optional<ProductInventory> inventoryOptional = productInventoryRepository.findByProductId(productId);
        ProductInventory inventory = inventoryOptional
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in Inventory"));

        int hasQuantity = inventory.getQuantity();
        int canPlaceQuantity = hasQuantity;

        if (isHighDemandProduct(productId)) {
            int maxAllowedQuantity = getMaxAllowedQuantity(productId);
            if (orderedQuantity > maxAllowedQuantity) {
                throw new HighDemandProductException(inventory.getProduct().getName()+" is a high demand product,max allowed quantity is "+maxAllowedQuantity);
            }
            if (hasQuantity >= maxAllowedQuantity) {
                canPlaceQuantity = maxAllowedQuantity;
            }
        }
        if (orderedQuantity > canPlaceQuantity) {
            throw new OutOfStockException(inventory.getProduct().getName()+" has only "+canPlaceQuantity+" stocks ");
        }
        return orderedQuantity;
    }
    public Boolean isHighDemandProduct(long productId)
    {
        Optional<HighDemandProduct> highDemandProductOptional=highDemandProductRepository.findByProductId(productId);
        if(highDemandProductOptional.isPresent())
        {
            return true;
        }
        return false;
    }
    public int getMaxAllowedQuantity(long productId)
    {
        Optional<HighDemandProduct> highDemandProductOptional=highDemandProductRepository.findByProductId(productId);
        if(highDemandProductOptional.isPresent())
        {
            HighDemandProduct highDemandProduct=highDemandProductOptional.get();
            return highDemandProduct.getMaxQuantity();
        }
        return 0;
    }
}
