package com.store.pteam.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Order_Items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "Game_id")
    private Game game;

}
