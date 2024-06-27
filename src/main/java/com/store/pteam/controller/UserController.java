package com.store.pteam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.store.pteam.model.Country;
import com.store.pteam.model.Game;
import com.store.pteam.model.PaymentDetails;
import com.store.pteam.model.User;
import com.store.pteam.service.CountryService;
import com.store.pteam.service.GameLibraryService;
import com.store.pteam.service.PaymentDetailsService;
import com.store.pteam.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

import javax.validation.Valid;

@Controller
@SessionAttributes("loggedInUser")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private GameLibraryService gameLibraryService;

    @Autowired
    private PaymentDetailsService paymentDetailsService;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(name = "error", required = false) String error) {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("user", new User());
        mav.addObject("error", error); // Add error flag to the view
        return mav;
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model){
        User loggedInUser = userService.login(user.getUsername(), user.getPassword());

        if(loggedInUser != null){
            model.addAttribute("loggedInUser", loggedInUser);
            return "redirect:/main"; // Redirect to main page after successful login
        } else {
            return "redirect:/login?error=true"; // Redirect with error flag
        }
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm(@RequestParam(name = "error", required = false) String error)  {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("error", error);
        mav.addObject("user", new User());
        mav.addObject("countries", countryService.getAllCountries());
        return mav;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid User user, BindingResult result,
                        @RequestParam("country.id") Long countryId, @RequestParam("phoneNumber") String phoneNumber,
                        @RequestParam("confirmPassword") String confirmPassword) {

        // Check if username already exists
        if (userService.usernameExists(user.getUsername())) {
            result.rejectValue("username", "error.user", "Username already exists.");
            return "redirect:/register?error=username"; // Return to registration form with error message
        }
    
        // Check if email address already exists
        if (userService.emailAddressExists(user.getEmailAddress())) {
            result.rejectValue("emailAddress", "error.user", "Email already exists.");
            return "redirect:/register?error=emailAddress"; // Return to registration form with error message
        }

        // Check if there are validation errors
        if (result.hasErrors()) {
            return "register"; // Return to registration form if there are binding errors
        }

        // Validate and format the phone number with the correct country code
        Country country = countryService.getCountryById(countryId);
        if (country == null) {
            result.rejectValue("country.id", "error.user", "Invalid country selected.");
            return "register"; // Return to registration form with error message
        }

        // Format the phone number with the country code
        String countryCode = country.getCountryCode();
        String fullPhoneNumber = formatPhoneNumber(countryCode, phoneNumber);
        if (userService.phoneNumberExists(fullPhoneNumber)) {
            result.rejectValue("phoneNumber", "error.user", "Phone number already in use.");
            return "redirect:/register?error=phoneNumber"; // Return to registration form with error message
        }

        // Set the formatted phone number in the user object
        user.setPhoneNumber(fullPhoneNumber);

        // Validate password confirmation
        if (!user.getPassword().equals(confirmPassword)) {
            result.rejectValue("password", "error.user", "Passwords do not match.");
            return "register"; // Return to registration form with error message
        }

        // Set user role and register user
        user.setRole("user");
        userService.register(user);

        return "redirect:/login"; // Redirect to login page after successful registration
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.removeAttribute("loggedInUser");
            session.invalidate();
        }
        // Set loggedInUser attribute to null in the model
        model.addAttribute("loggedInUser", null);
        return "redirect:/"; // Redirect to the login page after logging out
    }
    
    @GetMapping("/users")
    public String users(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; 
        }
        
        // Get the logged-in user's information
        model.addAttribute("user", loggedInUser);
        
        return "users"; 
    }

    @GetMapping("/gamelibrary")
    public String viewGameLibrary(@SessionAttribute("loggedInUser") User loggedInUser, Model model) {
        List<Game> games = gameLibraryService.getGamesByUser(loggedInUser);
        model.addAttribute("games", games);
        return "library";
    }

    @GetMapping("/purchase-history")
    public String showPurchaseHistory(@SessionAttribute("loggedInUser") User loggedInUser, Model model) {
        List<PaymentDetails> purchaseHistory = paymentDetailsService.getAllPaymentDetailsByUser(loggedInUser);
        model.addAttribute("purchaseHistory", purchaseHistory);
        return "purchase_history";
    }

    @GetMapping("/change-password")
    public ModelAndView showChangePasswordForm(@RequestParam(name = "error", required = false) String error, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView mav = new ModelAndView("pass_change");
        mav.addObject("errorMessage", error);
        return mav;
    }
    
    @PostMapping("/change-password")
    public String changePassword(@SessionAttribute("loggedInUser") User loggedInUser, 
                                 @RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmNewPassword") String confirmNewPassword,
                                 RedirectAttributes redirectAttributes, HttpSession session) {
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        
        if (newPassword.equals(currentPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "New passwords is the same as current one!");
            return "redirect:/change-password?error=true";
        }                            
        if (!newPassword.equals(confirmNewPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "New passwords do not match!");
            return "redirect:/change-password?error=true";
        }

        boolean isChanged = userService.changePassword(loggedInUser, currentPassword, newPassword);
        if (isChanged) {
            redirectAttributes.addFlashAttribute("successMessage", "Password changed successfully!");
            return "redirect:/users";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Current password is incorrect!");
            return "redirect:/change-password?error=true";
        }
    }

    @GetMapping("/restore-password")
    public ModelAndView showRestorePasswordForm(@RequestParam(name = "error", required = false) String error) {
        ModelAndView mav = new ModelAndView("restore_password");
        mav.addObject("errorMessage", error);
        return mav;
    }

    @PostMapping("/restore-password")
    public String restorePassword(@RequestParam("username") String username,
                                @RequestParam("emailAddress") String emailAddress,
                                @RequestParam("phoneNumber") String phoneNumber,
                                @RequestParam("newPassword") String newPassword,
                                @RequestParam("confirmNewPassword") String confirmNewPassword,
                                RedirectAttributes redirectAttributes) {

        boolean isRestored = userService.restorePassword(username, emailAddress, phoneNumber, newPassword);
        if (isRestored) {
            redirectAttributes.addFlashAttribute("successMessage", "Password restored successfully!");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to restore password. Please check your credentials.");
            return "redirect:/restore-password?error=true";
        }
    }

    private String formatPhoneNumber(String countryCode, String phoneNumber) {
        String cleanedPhoneNumber = phoneNumber.replaceAll("[^0-9]", "");
        return countryCode + cleanedPhoneNumber;
    }
}
