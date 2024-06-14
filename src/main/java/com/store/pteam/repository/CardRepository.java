package com.store.pteam.repository;

import com.store.pteam.model.Card;
import com.store.pteam.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByUser(User user);
    List<Card> findByUserId(Long userId);
    List<Card> findByUserAndCardStatus(User user, String cardStatus);
}
