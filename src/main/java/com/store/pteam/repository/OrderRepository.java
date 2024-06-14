package com.store.pteam.repository;

import com.store.pteam.model.Order;
import com.store.pteam.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    List<Order> findByStatus(String status);
    Order findByUser(User user);
    List<Order> findByUserAndStatus(User user, String status);
}

