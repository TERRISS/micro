package org.example.controller;

import org.example.model.User;
import org.example.userservice.UserService;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class Сontroller {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        userService.register(user);
        return ResponseEntity.ok("User  created");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null && new BCryptPasswordEncoder().matches(user.getPassword(), existingUser.getPassword())) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
    @GetMapping
    public boolean Id(@RequestParam Long userId) {
        User existingUser = userService.findById(userId);
        return !Objects.isNull(existingUser);
    }
}
