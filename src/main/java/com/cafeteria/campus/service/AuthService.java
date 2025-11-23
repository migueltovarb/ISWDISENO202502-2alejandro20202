package com.cafeteria.campus.service;

import com.cafeteria.campus.model.User;
import com.cafeteria.campus.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String email, String fullName, String password) {
        User u = new User();
        u.setEmail(email);
        u.setFullName(fullName);
        u.setPassword(passwordEncoder.encode(password));
        u.setRole("STUDENT");
        return userRepository.save(u);
    }

    public User validateUser(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .orElse(null);
    }
}
