package com.store.pteam.model;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Data
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "User_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @Column(name = "Total_price")
    private BigDecimal totalPrice;

    @Column(name = "Status")
    private String status = "pending";

    @CreationTimestamp
    private LocalDateTime createdTime;
    
    public Order() {
        this.orderItems = new ArrayList<>();
        this.totalPrice = BigDecimal.ZERO; 
    }

}
