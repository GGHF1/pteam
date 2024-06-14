package com.store.pteam.service;

import com.store.pteam.model.Cart;
import com.store.pteam.model.User;
import com.store.pteam.repository.CartRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public void addToCart(Cart cartItem) {
        cartRepository.save(cartItem);
    }

    public List<Cart> getCartItems(User user) {
        return cartRepository.findByUser(user);
    }

    public void removeFromCart(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    @Transactional
    public void clearCart(User user) {
        cartRepository.deleteByUser(user);
    }

}
