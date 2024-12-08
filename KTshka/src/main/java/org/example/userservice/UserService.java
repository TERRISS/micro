package org.example.userservice;

import org.example.model.User;
import org.example.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private Repository userRepository;

    public User register(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}