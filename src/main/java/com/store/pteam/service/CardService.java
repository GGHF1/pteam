package com.store.pteam.service;

import com.store.pteam.model.Card;
import com.store.pteam.model.User;
import com.store.pteam.repository.CardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public List<Card> getCardsByUser(User user) {
        return cardRepository.findByUser(user);
    }

    public List<Card> getCardsByUserId(Long userId) {
        return cardRepository.findByUserId(userId);
    }

    public Card getCardById(Long cardId) {
        return cardRepository.findById(cardId).orElse(null);
    }

    public List<Card> getActiveCardsByUser(User user) {
        return cardRepository.findByUserAndCardStatus(user, "Active");
    }

    public void updateCardStatuses() {
        List<Card> allCards = cardRepository.findAll();
        LocalDate today = LocalDate.now();
        for (Card card : allCards) {
            if (card.getExpirationDate().isBefore(today)) {
                card.setCardStatus("Inactive");
                cardRepository.save(card);
            }
        }
    }
    
}
