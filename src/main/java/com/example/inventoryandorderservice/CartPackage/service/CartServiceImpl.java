package com.example.inventoryandorderservice.CartPackage.service;

import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.Cart;
import com.example.inventoryandorderservice.model.CartItem;
import com.example.inventoryandorderservice.model.Product;
import com.example.inventoryandorderservice.repository.CartItemRepository;
import com.example.inventoryandorderservice.repository.CartRepository;
import com.example.inventoryandorderservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private CartItemRepository cartItemRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository,ProductRepository productRepository,CartItemRepository cartItemRepository){
        this.cartRepository=cartRepository;
        this.productRepository=productRepository;
        this.cartItemRepository=cartItemRepository;
    }
    @Override
    public Cart addToCart(long userId, long productId, int quantity) throws ResourceNotFoundException {
        Cart cart = cartRepository.findByCustomerId(userId).orElse(new Cart());
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id "+productId));

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        CartItem cartItem=null;
        if (existingItem.isPresent()) {
            cartItem=existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem= new CartItem();
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
    public Cart updateCartItem(long userId, long productId, int quantity) throws ResourceNotFoundException {
        Cart cart = cartRepository.findByCustomerId(userId).orElseThrow(() -> new ResourceNotFoundException("Cart not found with the userId "+userId));
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        if(existingItem.isEmpty())
        {
            throw new ResourceNotFoundException("CartItem not found with productId "+productId);
        }
        CartItem cartItem=existingItem.get();
        if(quantity==0)
        {
            deleteCartItem(cart,cartItem);
        }else{
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
            cart.setTotalPrice(calculateTotalPrice(cart));
             cartRepository.save(cart);
        }
        return cart;
    }

    @Override
    public List<CartItem> getCartItems(long userId) throws ResourceNotFoundException {
        Cart cart = cartRepository.findByCustomerId(userId).orElseThrow(() -> new ResourceNotFoundException("Cart not found for Customer with Id "+userId));
        return cart.getCartItems();
    }

    public void deleteCartItem(Cart cart,CartItem cartItem) {
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
}
