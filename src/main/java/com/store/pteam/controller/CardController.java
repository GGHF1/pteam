package com.store.pteam.controller;

import com.store.pteam.model.Card;
import com.store.pteam.model.User;
import com.store.pteam.service.CardService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/addcard")
    public String addCard(@SessionAttribute("loggedInUser") User loggedInUser,
                        @RequestParam("cardNumber") String cardNumber,
                        @RequestParam("cardHolderName") String cardHolderName,
                        @RequestParam("expirationDate") String expirationDateString,
                        @RequestParam("cvv") String cvv,
                        RedirectAttributes redirectAttributes) {

        // Determine the card service type
        String serviceType = determineCardServiceType(cardNumber);
        if (serviceType == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid card number!");
            return "redirect:/addcard";
        }

        // Parse the expiration date string into a LocalDate object
        LocalDate expirationDate;
        try {
            // Parse the expiration date string into a YearMonth object
            YearMonth expirationYearMonth = YearMonth.parse(expirationDateString, DateTimeFormatter.ofPattern("yyyy-MM"));
            
            // Set the expiration date to the last day of the month
            expirationDate = expirationYearMonth.atEndOfMonth();
        } catch (DateTimeParseException e) {
            // Handle invalid date format
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid expiration date format!");
            return "redirect:/addcard";
        }

        // Convert the cardholder name to uppercase
        String uppercaseCardHolderName = cardHolderName.toUpperCase();

        // Create a new card object with status set to "Active"
        Card card = new Card(cardNumber, uppercaseCardHolderName, expirationDate, cvv, serviceType, "Active");
        card.setUser(loggedInUser);

        // Save the card to the database
        cardService.saveCard(card);

        // Redirect to a success page
        redirectAttributes.addFlashAttribute("successMessage", "Card added successfully!");
        return "redirect:/users"; // Redirect to the user's profile page
    }

    @GetMapping("/addcard")
    public String showAddCardForm(@SessionAttribute("loggedInUser") User loggedInUser, Model model) {
        return "card"; // Assuming you have an HTML template named "card.html"
    }

    @GetMapping("/cardinfo")
    public String viewCards(@SessionAttribute("loggedInUser") User loggedInUser, Model model) {
        // Retrieve cards for the logged-in user from the database
        List<Card> userCards = cardService.getCardsByUser(loggedInUser);
        
        // Add the list of user cards to the model to be accessed in the HTML file
        model.addAttribute("userCards", userCards);
        
        // Return the name of the HTML file to be rendered
        return "card_info"; // Assuming you have an HTML template named "card_info.html"
    }

    private String determineCardServiceType(String cardNumber) {
        if (cardNumber.startsWith("34") || cardNumber.startsWith("37")) {
            return "American Express";
        } else if (cardNumber.startsWith("4")) {
            return "Visa";
        } else if (cardNumber.startsWith("5") && (cardNumber.charAt(1) >= '1' && cardNumber.charAt(1) <= '5')) {
            return "Mastercard";
        } else if (cardNumber.startsWith("2") && (Integer.parseInt(cardNumber.substring(0, 4)) >= 2221 && Integer.parseInt(cardNumber.substring(0, 4)) <= 2720)) {
            return "Mastercard";
        }
        return null; // Invalid card number
    }    
}
