package com.library.library.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.library.management.entity.User;
import com.library.library.management.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserRepository userRepository;

@PostMapping("/login")
public User login(@RequestBody User loginRequest) {

    String username = loginRequest.getUsername();
    String password = loginRequest.getPassword();

    // 🔍 Check if user already exists
    User user = userRepository.findByUsername(username).orElse(null);

    // 🆕 NEW USER → AUTO REGISTER
    if (user == null) {

        // ❌ Do NOT allow new admins
        if ("ADMIN".equalsIgnoreCase(loginRequest.getRole())) {
            throw new RuntimeException("Admin already exists");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole("USER");

        return userRepository.save(newUser);
    }

    // 🔐 EXISTING USER → PASSWORD CHECK
    if (!user.getPassword().equals(password)) {
        throw new RuntimeException("Invalid password");
    }

    return user;
}



}

