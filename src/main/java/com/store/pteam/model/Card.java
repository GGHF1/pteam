package com.store.pteam.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cards")
public class Card {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String cardNumber;
    
    @Column(nullable = false)
    private String cardHolderName;
    
    @Column(nullable = false)
    private LocalDate expirationDate;
    
    @Column(nullable = false)
    private String cvv;

    @Column(nullable = false)
    private String serviceType;

    @Column(nullable = false)
    private String cardStatus;

    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Card(String cardNumber, String cardHolderName, LocalDate expirationDate, String cvv, String serviceType, String cardStatus) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.serviceType = serviceType;
        this.cardStatus = cardStatus;
    }
}
