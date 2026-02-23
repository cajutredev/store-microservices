package com.store.order_service.controller;


import com.store.order_service.dto.CartItemDto;
import com.store.order_service.entity.CartItem;
import com.store.order_service.service.CartService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@NoArgsConstructor
public class CartItemController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody CartItemDto cartItemDto) {

        if (!cartService.addToCart(Long.parseLong(userId), cartItemDto)) {
            return ResponseEntity.badRequest().body("Product out of order");
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Boolean> removeFromCart(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable Long productId) {

        boolean deleted = cartService.deleteItemFromCart(
                Long.valueOf(userId), productId);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems(
            @RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(cartService.getCartItems(Long.valueOf(userId)));
    }
}
