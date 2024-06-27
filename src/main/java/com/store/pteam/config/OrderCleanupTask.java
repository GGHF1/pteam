package com.store.pteam.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.store.pteam.service.OrderService;

@Component
public class OrderCleanupTask {

    private final OrderService orderService;

    public OrderCleanupTask(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(fixedRate = 600000) 
    public void cleanupPendingOrders() {
        orderService.deleteExpiredOrders();
    }
}
