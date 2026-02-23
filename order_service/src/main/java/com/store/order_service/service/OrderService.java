package com.store.order_service.service;


import com.store.order_service.dto.OrderDto;
import com.store.order_service.entity.OrderStatus;
import com.store.order_service.entity.CartItem;
import com.store.order_service.entity.Order;
import com.store.order_service.entity.OrderItem;
import com.store.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
    private final OrderRepository orderRepository;
//    private final UserRepository userRepository;
    private ModelMapper modelMapper;


    public Optional<OrderDto> createOrder(Long userId) {
        // Validate for cart items
        List<CartItem> cartItems = cartService.getCartItems(userId);
        if (cartItems.isEmpty()) {
            return Optional.empty();
        }
//        // Validate for user

//        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
//        if (userOptional.isEmpty()) {
//            return Optional.empty();
//        }
//        User user = userOptional.get();

        // Calculate total price
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create order
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmout(totalPrice);

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(
                        null,
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                ))
                .toList();

        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        // Clear the cart
        cartService.clearCart(userId);

        // Publish order created event
//        OrderCreatedEvent event = new OrderCreatedEvent(
//                savedOrder.getId(),
//                savedOrder.getUserId(),
//                savedOrder.getStatus(),
//                mapToOrderItemDTOs(savedOrder.getItems()),
//                savedOrder.getTotalAmount(),
//                savedOrder.getCreatedAt()
//        );
//        streamBridge.send("createOrder-out-0", event);


        return Optional.of(modelMapper.map(savedOrder, OrderDto.class));
    }
}
