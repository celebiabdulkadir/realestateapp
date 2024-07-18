package com.abdulkadir.auth.service;

import com.abdulkadir.auth.dto.request.UserCreateDTO;
import com.abdulkadir.auth.exception.EntityAlreadyExistsException;
import com.abdulkadir.auth.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String createUser(UserCreateDTO userCreateDTO) {
        // Assuming email is the unique identifier. Adjust according to your user model.
        String email = userCreateDTO.getEmail();
        boolean userExists = repository.findByEmail(email).isPresent();

        if (userExists) {
            throw new EntityAlreadyExistsException("User already exists with email: " + email);
        }

        userCreateDTO.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        return "User created successfully!";
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);  // Ensure both token and username are passed here
    }
}