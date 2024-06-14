package com.store.pteam.repository;

import com.store.pteam.model.PaymentDetails;
import com.store.pteam.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {
    List<PaymentDetails> findByUser(User user);
}
