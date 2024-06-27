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
class RestorePasswordTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRestorePassValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/restore-password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "gghf")
                .param("emailAddress", "artems@gmail.com")
                .param("phoneNumber", "+37127418888")
                .param("newPassword", "Art123!@")
                .param("confirmNewPassword", "Art123!@"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login")); 
    }

    @Test
    void testRestorePassInvalidUsername() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/restore-password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "gghf1")
                .param("emailAddress", "artems@gmail.com")
                .param("phoneNumber", "+37127418888")
                .param("newPassword", "Art123!@")
                .param("confirmNewPassword", "Art123!@"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/restore-password?error=true")); 
    }

    @Test
    void testRestorePassInvalidEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/restore-password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "gghf")
                .param("emailAddress", "artems@gmail.lv")
                .param("phoneNumber", "+37127418888")
                .param("newPassword", "Art123!@")
                .param("confirmNewPassword", "Art123!@"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/restore-password?error=true")); 
    }

    @Test
    void testRestorePassInvalidPhNumber() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/restore-password")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "gghf")
                .param("emailAddress", "artems@gmail.com")
                .param("phoneNumber", "+327418888")
                .param("newPassword", "Art123!@")
                .param("confirmNewPassword", "Art123!@"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/restore-password?error=true")); 
    }
    
}
