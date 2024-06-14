package com.store.pteam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.pteam.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
    // You can add custom methods here if needed
}
