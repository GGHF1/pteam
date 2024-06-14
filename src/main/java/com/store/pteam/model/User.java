package com.store.pteam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_id")
    private Long id;
    
    @Column(name = "Username")
    private String username;
    
    @Column(name = "First_name")
    private String fName;

    @Column(name = "Last_name")
    private String lName;

    @Column(name = "Email_address")
    private String emailAddress;
    
    @Column(name = "Password")
    private String password;
    
    @Column(name = "Date_of_birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    
    @Column(name = "Date_of_registration")
    private Date dateOfRegistration;

    @Column(name = "Phone_number")
    private String phoneNumber;

    @Column(name = "Role") 
    private String role;

    @Column(name = "Gender")
    private String gender;

    @ManyToOne
    @JoinColumn(name = "Country_Id")
    private Country country;

}
