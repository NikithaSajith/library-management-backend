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
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public User login(@RequestBody User loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        User user = userRepository
                .findByUsernameAndStatus(username, 0)
                .orElse(null);

        // AUTO REGISTER ONLY NORMAL USERS
        if (user == null) {

            if ("ADMIN".equalsIgnoreCase(loginRequest.getRole())) {
                throw new RuntimeException("Admin already exists");
            }

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setRole("USER");
            newUser.setStatus(0);

            return userRepository.save(newUser);
        }

        // PASSWORD CHECK
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }
}
