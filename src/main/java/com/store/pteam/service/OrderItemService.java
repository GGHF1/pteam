package com.store.pteam.service;

import com.store.pteam.model.Order;
import com.store.pteam.model.OrderItem;
import com.store.pteam.repository.OrderItemRepository;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public void addToOrder(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    @Transactional
    public List<OrderItem> getOrderItemsByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }
}
