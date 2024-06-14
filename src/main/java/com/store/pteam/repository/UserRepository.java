package com.store.pteam.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.store.pteam.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);
    User findByUsernameAndEmailAddressAndPhoneNumber(String username, String emailAddress, String phoneNumber);
    User findByPhoneNumber(String phoneNumber);
    User findByEmailAddress(String emailAddress);
}