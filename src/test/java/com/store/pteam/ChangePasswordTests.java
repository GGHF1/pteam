package com.store.pteam;

import com.store.pteam.controller.UserController;
import com.store.pteam.model.User;
import com.store.pteam.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ChangePasswordTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void ChangePasswordSuccessful() throws Exception {
        User loggedInUser = new User();
        loggedInUser.setUsername("gghf");
        loggedInUser.setPassword("Art123!@");

        when(userService.changePassword(eq(loggedInUser), anyString(), anyString())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/change-password")
                .param("currentPassword", "Art123!@")
                .param("newPassword", "123Art!@")
                .param("confirmNewPassword", "123Art!@")
                .sessionAttr("loggedInUser", loggedInUser))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/users"));

        verify(userService, times(1)).changePassword(eq(loggedInUser), eq("Art123!@"), eq("123Art!@"));
    }

    @Test
    public void ChangePasswordIncorrectCurrentPassword() throws Exception {
        User loggedInUser = new User();
        loggedInUser.setUsername("gghf");
        loggedInUser.setPassword("Art123!@");

        when(userService.changePassword(eq(loggedInUser), anyString(), anyString())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/change-password")
                .param("currentPassword", "Art123!!")
                .param("newPassword", "123Art!@")
                .param("confirmNewPassword", "123Art!@")
                .sessionAttr("loggedInUser", loggedInUser))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/change-password?error=true"));

        verify(userService, times(1)).changePassword(eq(loggedInUser), eq("Art123!!"), eq("123Art!@"));
    }

    @Test
    public void NewPasswordSameAsCurrent() throws Exception {
        User loggedInUser = new User();
        loggedInUser.setUsername("gghf");
        loggedInUser.setPassword("Art123!@");

        when(userService.changePassword(eq(loggedInUser), anyString(), anyString())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/change-password")
                .param("currentPassword", "Art123!@")
                .param("newPassword", "Art123!@")
                .param("confirmNewPassword", "Art123!@")
                .sessionAttr("loggedInUser", loggedInUser))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/change-password?error=true"));
    }
}
