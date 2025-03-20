package com.bcast.ai_demo.services;

import com.bcast.ai_demo.dtos.RegistrationRequest;
import com.bcast.ai_demo.models.User;
import com.bcast.ai_demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public User registerNewUser(RegistrationRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }
}