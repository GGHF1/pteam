package com.store.pteam.service;

import com.store.pteam.model.Order;
import com.store.pteam.model.OrderItem;
import com.store.pteam.model.User;
import com.store.pteam.repository.OrderItemRepository;
import com.store.pteam.repository.OrderRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    @Transactional
    public Order getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    @Transactional
    public void updateOrder(Order order) {
        orderRepository.save(order);
    }
    
    public List<OrderItem> getOrderItemsByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }

    public boolean hasPendingOrders(User user) {
        List<Order> pendingOrders = orderRepository.findByUserAndStatus(user, "pending");
        return !pendingOrders.isEmpty();
    }

    public List<Order> getPendingOrders(User user) {
        return orderRepository.findByUserAndStatus(user, "pending");
    }

    

    public void deleteOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            // Remove order items
            order.getOrderItems().clear();
            orderRepository.save(order); 
            // Delete order
            orderRepository.delete(order);
        }
    }

    @Transactional
    public void deleteExpiredOrders() {
        List<Order> pendingOrders = orderRepository.findByStatus("pending");
        LocalDateTime now = LocalDateTime.now();
        for (Order order : pendingOrders) {
            LocalDateTime cutoffTime = order.getCreatedTime().plusMinutes(20); 
            if (now.isAfter(cutoffTime)) {
                // Remove order items
                List<OrderItem> orderItems = order.getOrderItems();
                for (OrderItem item : orderItems) {
                    orderItemRepository.delete(item);
                }
                // Delete order
                orderRepository.delete(order);
            }
        }
    }

    public Order getPendingOrder(User user) {
        List<Order> pendingOrders = orderRepository.findByUserAndStatus(user, "pending");
        if (!pendingOrders.isEmpty()) {
            return pendingOrders.get(0); 
        }
        return null; 
    }

}
