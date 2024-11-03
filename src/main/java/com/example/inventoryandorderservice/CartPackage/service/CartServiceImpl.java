package com.example.inventoryandorderservice.CartPackage.service;

import com.example.inventoryandorderservice.exceptions.HighDemandProductException;
import com.example.inventoryandorderservice.exceptions.OutOfStockException;
import com.example.inventoryandorderservice.exceptions.ResourceNotFoundException;
import com.example.inventoryandorderservice.model.*;
import com.example.inventoryandorderservice.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private CartItemRepository cartItemRepository;
    private ProductInventoryRepository productInventoryRepository;
    private HighDemandProductRepository highDemandProductRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository,
                           ProductInventoryRepository productInventoryRepository, HighDemandProductRepository highDemandProductRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.productInventoryRepository = productInventoryRepository;
        this.highDemandProductRepository=highDemandProductRepository;
    }

    @Override
    public Cart addOrUpdateCartItem(long userId, long productId, int quantity) throws Exception {
        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
        Cart cart=null;
        if(cartOptional.isEmpty())
        {
            cart=new Cart();
            cart.setCartItems(new ArrayList<>());
            cart.setUserId(userId);
            cart=cartRepository.save(cart);
        }else {
            cart=cartOptional.get();
        }
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty())
        {
            throw new ResourceNotFoundException("Product not found with id " + productId);
        }
        Product product=productOptional.get();
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        CartItem cartItem = null;
        if(existingItem.isPresent() && quantity == 0 )
        {
            cart=deleteCartItem(cart, existingItem.get());
            return cart;
        }else if(existingItem.isEmpty() && quantity == 0 )
        {
            throw new Exception("quantity should be AtLeast 1");
        }
        int orderedQuantity=verifyMaxPossibleQuantity(productId,quantity);
        if (existingItem.isEmpty()) {
            cartItem=new CartItem();
            cartItem.setQuantity(0);
            cartItem=cartItemRepository.save(cartItem);
            cart.getCartItems().add(cartItem);
        }else {
            cartItem = existingItem.get();
        }

        //updating inventory
        updateProductInventoryQuantity(productId,cartItem.getQuantity(),orderedQuantity);

        cartItem.setProduct(product);
        cartItem.setQuantity(orderedQuantity);
        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);
        cart.setTotalPrice(calculateTotalPrice(cart));
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public Cart getCartItems(long userId) throws ResourceNotFoundException {
        return cartRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Cart not found for Customer with Id " + userId));
    }

    @Override
    public Double getCartValue(long userId) throws ResourceNotFoundException {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Cart not found for Customer with Id " + userId));
        return cart.getTotalPrice();
    }
    public Cart deleteCartItem(Cart cart, CartItem cartItem) {
        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        cart.setTotalPrice(calculateTotalPrice(cart));
        return cartRepository.save(cart);
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

    public void updateProductInventoryQuantity(long productId,int currentQuantity,int newQuantity) throws ResourceNotFoundException {
        Optional<ProductInventory> inventoryOptional = productInventoryRepository.findByProductId(productId);
        ProductInventory inventory = inventoryOptional
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in Inventory"));
        int differenceQuantity=0;
        if(newQuantity>currentQuantity)
        {
            differenceQuantity=newQuantity-currentQuantity;
            inventory.setQuantity(inventory.getQuantity()-differenceQuantity);
        }else {
            differenceQuantity=currentQuantity-newQuantity;
            inventory.setQuantity(inventory.getQuantity()+differenceQuantity);
        }
        productInventoryRepository.save(inventory);
    }
}
