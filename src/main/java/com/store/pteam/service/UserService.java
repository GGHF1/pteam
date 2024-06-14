package com.store.pteam.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.store.pteam.model.User;
import com.store.pteam.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repo;

    public User login(String username, String password) {
        return repo.findByUsernameAndPassword(username, password);
    }

    @Transactional
    public void register(User user) {
        // Data validation
        if (user == null || user.getUsername() == null || user.getPassword() == null ||
                user.getEmailAddress() == null || user.getFName() == null ||
                user.getLName() == null || user.getDateOfBirth() == null ||
                user.getCountry() == null || user.getPhoneNumber() == null) {
            throw new IllegalArgumentException("Invalid user data");
        }

        // Check for duplicate user
        if (repo.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Set registration date
        user.setDateOfRegistration(new Date());

        // Save user to the database
        repo.save(user);
    }

    @Transactional
    public boolean changePassword(User user, String currentPassword, String newPassword) {
        User existingUser = repo.findById(user.getId())
                                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Check if the current password is correct
        if (!existingUser.getPassword().equals(currentPassword)) {
            return false;
        }

        // Update the password
        existingUser.setPassword(newPassword);
        repo.save(existingUser);
        return true;
    }

    public boolean restorePassword(String username, String emailAddress, String phoneNumber, String newPassword) {
        User user = repo.findByUsernameAndEmailAddressAndPhoneNumber(username, emailAddress, phoneNumber);
        if (user != null) {
            user.setPassword(newPassword);
            repo.save(user);
            return true;
        }
        return false;
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public boolean usernameExists(String username) {
        User user = repo.findByUsername(username);
        return user != null;
    }

    public boolean phoneNumberExists(String phoneNumber) {
        User user = repo.findByPhoneNumber(phoneNumber);
        return user != null;
    }

    public boolean emailAddressExists(String emailAddress) {
        User user = repo.findByEmailAddress(emailAddress);
        return user != null;
    }
}
