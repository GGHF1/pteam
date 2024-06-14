package com.store.pteam.service;

import com.store.pteam.model.PaymentDetails;
import com.store.pteam.model.User;
import com.store.pteam.repository.PaymentDetailsRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PaymentDetailsService {

    private final PaymentDetailsRepository paymentDetailsRepository;

    public PaymentDetailsService(PaymentDetailsRepository paymentDetailsRepository) {
        this.paymentDetailsRepository = paymentDetailsRepository;
    }

    public void savePaymentDetails(PaymentDetails paymentDetails) {
        paymentDetailsRepository.save(paymentDetails);
    }

    public List<PaymentDetails> getAllPaymentDetails() {
        return paymentDetailsRepository.findAll();
    }

    public List<PaymentDetails> getAllPaymentDetailsByUser(User user) {
        return paymentDetailsRepository.findByUser(user);
    }
}
