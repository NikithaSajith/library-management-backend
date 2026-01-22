package com.library.library.management.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.library.library.management.entity.User;
import com.library.library.management.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE USER (ADMIN)
    @PostMapping
    public User addUser(@RequestBody User user) {
        user.setStatus(0);
        return userRepository.save(user);
    }

    // READ USERS (ACTIVE ONLY)
    @GetMapping
    public List<User> getUsers() {
        return userRepository.findByStatus(0);
    }

    // UPDATE USER
// UPDATE USER
@PutMapping("/{id}")
public User updateUser(@PathVariable Long id, @RequestBody User updated) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // ❌ BLOCK ADMIN EDIT
    if ("ADMIN".equalsIgnoreCase(user.getRole())) {
        throw new RuntimeException("Admin cannot be edited");
    }

    user.setUsername(updated.getUsername());

    if (updated.getPassword() != null && !updated.getPassword().isBlank()) {
        user.setPassword(updated.getPassword());
    }

    return userRepository.save(user);
}


  // SOFT DELETE USER
@DeleteMapping("/{id}")
public void deleteUser(@PathVariable Long id) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // ❌ BLOCK ADMIN DELETE
    if ("ADMIN".equalsIgnoreCase(user.getRole())) {
        throw new RuntimeException("Admin cannot be deleted");
    }

    user.setStatus(1);
    userRepository.save(user);
}

}
