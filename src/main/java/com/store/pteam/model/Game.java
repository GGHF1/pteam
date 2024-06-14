package com.store.pteam.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Data
@Table(name = "Game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Game_id")
    private Long id;
    
    @Column(name = "Title")
    private String title;
    
    @Column(name = "Genre")
    private String genre;
    
    @Column(name = "Description")
    private String description;
    
    @Column(name = "Release_Date")
    private Date releaseDate;
    
    @Column(name = "Developer")
    private String developer;
    
    @Column(name = "Publisher")
    private String publisher;

    @Column(name = "Price")  
    private BigDecimal price;

    
}
