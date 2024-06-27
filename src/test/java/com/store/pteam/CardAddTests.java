package com.store.pteam;

import com.store.pteam.controller.CardController;
import com.store.pteam.model.User;
import com.store.pteam.service.CardService;
import com.store.pteam.service.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest(CardController.class)
@AutoConfigureMockMvc
public class CardAddTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @MockBean
    private UserService userService;

    @Test
    public void AddCardSuccessful() throws Exception {
        User loggedInUser = new User(); //this page requires a logged in user
        loggedInUser.setUsername("gghf");
        loggedInUser.setPassword("Art123!@");
        
        when(userService.login(anyString(), anyString())).thenReturn(loggedInUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/addcard")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("cardNumber", "4111111111111111")
                .param("cardHolderName", "Artem Doe")
                .param("expirationDate", "2024-12")
                .param("cvv", "123")
                .sessionAttr("loggedInUser", loggedInUser))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/users"));
    }

    @Test
    public void AddCardsWithInvalidCardNumber() throws Exception {
        User loggedInUser = new User();
        loggedInUser.setUsername("gghf");
        loggedInUser.setPassword("Art123!@");
        
        when(userService.login(anyString(), anyString())).thenReturn(loggedInUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/addcard")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("cardNumber", "!@!@##!@#@")
                .param("cardHolderName", "Artem Doe")
                .param("expirationDate", "2024-12")
                .param("cvv", "123")
                .sessionAttr("loggedInUser", loggedInUser))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/addcard"));
    }

}
