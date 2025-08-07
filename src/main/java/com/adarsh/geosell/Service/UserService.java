package com.adarsh.geosell.Service;

import com.adarsh.geosell.Entity.User;
import com.adarsh.geosell.Enum.BusinessType;
import com.adarsh.geosell.Enum.SubscriptionType;
import com.adarsh.geosell.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getUsersByBusinessType(BusinessType businessType) {
        return userRepository.findByBusinessType(businessType);
    }

    public List<User> getUsersBySubscriptionType(SubscriptionType subscriptionType) {
        return userRepository.findBySubscriptionType(subscriptionType);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Long countUsersBySubscription(SubscriptionType subscriptionType) {
        return userRepository.countBySubscriptionType(subscriptionType);
    }
}