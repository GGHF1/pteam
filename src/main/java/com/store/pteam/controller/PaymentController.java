package com.store.pteam.controller;

import com.store.pteam.model.Card;
import com.store.pteam.model.Order;
import com.store.pteam.model.OrderItem;
import com.store.pteam.model.PaymentDetails;
import com.store.pteam.model.User;
import com.store.pteam.service.CardService;
import com.store.pteam.service.GameLibraryService;
import com.store.pteam.service.OrderItemService;
import com.store.pteam.service.OrderService;
import com.store.pteam.service.PaymentDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PaymentController {

    @Autowired
    private CardService cardService;

    @Autowired
    private OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    GameLibraryService gameLibraryService;

    @Autowired
    PaymentDetailsService paymentDetailsService;

    @GetMapping("/payment")
    public String displayPaymentPage(@SessionAttribute("loggedInUser") User loggedInUser, Model model) {
        // Get the user's cards
        List<Card> userCards = cardService.getActiveCardsByUser(loggedInUser); // Filter out inactive cards
    
        // Check if the user has any pending orders
        boolean hasPendingOrders = orderService.hasPendingOrders(loggedInUser);
        // If the user has pending orders, retrieve and add them to the model
        List<Order> pendingOrders = null;
        if (hasPendingOrders) {
            pendingOrders = orderService.getPendingOrders(loggedInUser);
        }
        // Get the country of the logged-in user
        String userCountry = loggedInUser.getCountry().getName();
    
    
        model.addAttribute("userCards", userCards);
        model.addAttribute("hasPendingOrders", hasPendingOrders);
        model.addAttribute("pendingOrders", pendingOrders); // Add pending orders to the model
        model.addAttribute("userCountry", userCountry);
        model.addAttribute("paymentDetails", new PaymentDetails());
    
        return "payment";
    }
    
    @PostMapping("/payment")
    public String processPayment(@SessionAttribute("loggedInUser") User loggedInUser, PaymentDetails paymentDetails, @RequestParam("cardId") Long cardId, @RequestParam("cvv") String cvv) {
        // Retrieve the card associated with the selected card ID
        Card selectedCard = cardService.getCardById(cardId);
        
        // Check if the entered CVV matches the one stored for the selected card
        if (selectedCard.getCvv().equals(cvv)) {
            // CVV code matches, proceed with payment processing
            List<Order> pendingOrders = orderService.getPendingOrders(loggedInUser);

            for (Order order : pendingOrders) {
                // If the order status is not "success", process the payment for that order
                if (!order.getStatus().equalsIgnoreCase("success")) {
                    // Populate payment details
                    paymentDetails.setUser(loggedInUser);
                    paymentDetails.setOrder(order);
                    paymentDetails.setCard(selectedCard);
                    paymentDetails.setPurchasedTime(LocalDateTime.now());
                    paymentDetails.setCountry(loggedInUser.getCountry().getName());
                    paymentDetails.setCvv(cvv); 
                    // Save payment details
                    paymentDetailsService.savePaymentDetails(paymentDetails);
                    // Update order status
                    order.setStatus("success");
                    orderService.updateOrder(order);
                    
                    // Process order items 
                    List<OrderItem> orderItems = orderItemService.getOrderItemsByOrder(order);
                    for (OrderItem orderItem : orderItems) {
                        gameLibraryService.addGameToLibrary(loggedInUser, orderItem.getGame());
                    }
                }
            }
            
            return "redirect:/main"; 

        } else {
            // CVV code does not match
            return "error"; 
        }
    }

    @GetMapping("/terms-and-conditions")
    public String displayTermsAndConditions() {
        return "terms_and_conditions";
    }
}
