package com.example.cartact.controller;

import com.example.cartact.model.CartItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final List<CartItem> cartItemLists = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    // Get all users
    @GetMapping
    public List<CartItem> getAllCartItems() {
        return cartItemLists;
    }

    // Get cartItem by ID
    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getItemById(@PathVariable Long id) {
        Optional<CartItem> Item = cartItemLists.stream().filter(u -> u.getId().equals(id)).findFirst();
        return Item.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create new Cart Item
    @PostMapping("/addItem")
    public ResponseEntity<CartItem> createItem(@RequestBody CartItem cartItemList) {
        cartItemList.setId(counter.incrementAndGet());
        cartItemLists.add(cartItemList);
        return new ResponseEntity<>(cartItemList, HttpStatus.CREATED);
    }

    // Delete Cart Item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        Optional<CartItem> cartItemOptional = cartItemLists.stream().filter(u -> u.getId().equals(id)).findFirst();
        if (cartItemOptional.isPresent()) {
            cartItemLists.remove(cartItemOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}