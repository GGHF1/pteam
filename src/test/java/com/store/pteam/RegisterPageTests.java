package com.store.pteam;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RegisterPageTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRegisterValidUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("fName", "Artem")
                .param("lName", "Doe")
                .param("username", "artdoe")
                .param("password", "Password123!")
                .param("confirmPassword", "Password123!")
                .param("gender", "Male")
                .param("country.id", "1") 
                .param("dateOfBirth", "1990-01-01")
                .param("phoneNumber", "1234567890")
                .param("emailAddress", "john.doe@example.com"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login")); 
    }

    @Test
    void testRegisterWithExistUsername() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("fName", "Artem")
                .param("lName", "Doe")
                .param("username", "gghf") //username already exists
                .param("password", "Password123!")
                .param("confirmPassword", "Password123!")
                .param("gender", "Male")
                .param("country.id", "1") 
                .param("dateOfBirth", "1990-01-01")
                .param("phoneNumber", "1234567890")
                .param("emailAddress", "john.doe@example.com"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register?error=username")); 
    }

    @Test
    void testRegisterWithExistEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("fName", "Artem")
                .param("lName", "Doe")
                .param("username", "artdoe")
                .param("password", "Password123!")
                .param("confirmPassword", "Password123!")
                .param("gender", "Male")
                .param("country.id", "1") 
                .param("dateOfBirth", "1990-01-01")
                .param("phoneNumber", "1234567890")
                .param("emailAddress", "artems@gmail.com")) //email already exists
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register?error=emailAddress")); 
    }

    @Test
    void testRegisterWithExistPhNumber() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("fName", "Artem")
                .param("lName", "Doe")
                .param("username", "artdoe") 
                .param("password", "Password123!")
                .param("confirmPassword", "Password123!")
                .param("gender", "Male")
                .param("country.id", "20") 
                .param("dateOfBirth", "1990-01-01")
                .param("phoneNumber", "27418888") //phone number already exists
                .param("emailAddress", "john.doe@example.com"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register?error=phoneNumber")); 
    }
    
}
