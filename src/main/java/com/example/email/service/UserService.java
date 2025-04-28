package com.example.email.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.email.model.User;
import com.example.email.repository.UserRepository;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public Optional<User> authenticateUser(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
    
    public boolean registerUser(User user) {
        // checking if user already exists
        if (userRepository.existsById(user.getEmail())) {
            return false;
        }
        user.setRegistration_date(LocalDate.now());
        userRepository.save(user);
        return true;
    }
    
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findById(email);
    }

    public Optional<User> findByEmailAndPhone(String email, String phone) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPhone() != null && user.getPhone().equals(phone) 
                ) {
                return userOpt;
            }
        }
        
        return Optional.empty();
    }
    
    public boolean updatePassword(String email, String newPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        
        return false;
    }
}

