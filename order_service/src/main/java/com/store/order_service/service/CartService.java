package com.store.order_service.service;


import com.store.order_service.dto.CartItemDto;
import com.store.order_service.entity.CartItem;
import com.store.order_service.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

//    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
//    private final UserRepository userRepository;

    public boolean addToCart(Long userId, CartItemDto cartItemDto) {

//        Optional<Product> productOpt = productRepository.findById(cartItemDto.getProductId());
//
//        if (productOpt.isEmpty()) {
//            return false;
//        }
//
//        Product product = productOpt.get();
//        if (product.getStock() < cartItemDto.getQuantity()) {
//            return false;
//        }
//
//        Optional<User> userOpt = userRepository.findById(Long.parseLong(userId));
//
//        if (userOpt.isEmpty()) {
//            return false;
//        }

//        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(
            userId, cartItemDto.getProductId());

        if (existingCartItem != null) {
           existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemDto.getQuantity());
           existingCartItem.setPrice(BigDecimal.valueOf(100.00));
           cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(cartItemDto.getProductId());
            cartItem.setQuantity(cartItemDto.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(100.00));
            cartItemRepository.save(cartItem);
        }
        return true;
    }


    public boolean deleteItemFromCart(Long userId, Long productId) {
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(
                userId, productId);

        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
            return true;
        }
        return false;
    }

    public List<CartItem> getCartItems(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
