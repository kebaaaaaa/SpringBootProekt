package com.avtopark.avtopark.service;

import com.avtopark.avtopark.entity.Role;
import com.avtopark.avtopark.entity.User;
import com.avtopark.avtopark.repository.RoleRepository;
import com.avtopark.avtopark.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }
    
    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }
    
    public User saveUser(User user) {
        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
    
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }
    
    public User registerUser(User user) {
        if (emailExists(user.getEmail()) || usernameExists(user.getUsername())) {
            throw new IllegalArgumentException("Email or username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Set default role as USER if not specified
        if (user.getRole() == null) {
            Role userRole = roleRepository.findByName("USER");
            if (userRole == null) {
                throw new IllegalStateException("USER role not found in database");
            }
            user.setRole(userRole);
        }
        return userRepository.save(user);
    }
}