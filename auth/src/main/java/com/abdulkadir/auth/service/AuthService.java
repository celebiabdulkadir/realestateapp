package com.abdulkadir.auth.service;

import com.abdulkadir.auth.dto.request.UserCreateDTO;
import com.abdulkadir.auth.dto.response.AuthResponseDTO;
import com.abdulkadir.auth.exception.EntityAlreadyExistsException;
import com.abdulkadir.auth.model.Auth;
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
        String username = userCreateDTO.getUsername();
        boolean userExists = repository.findByEmail(email).isPresent() || repository.findByUsername(username).isPresent();

        if (userExists) {
            throw new EntityAlreadyExistsException("User already exists with email: " + email + " or username: " + username);
        }

        userCreateDTO.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        Auth newUser = new Auth();
        newUser.setName(userCreateDTO.getName());
        newUser.setSurname(userCreateDTO.getSurname());
        newUser.setPhoneNumber(userCreateDTO.getPhoneNumber());
        newUser.setAddress(userCreateDTO.getAddress());
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(userCreateDTO.getPassword());

        // Save the new user entity to the database
        repository.save(newUser);

        return "User created successfully!";
    }

    public AuthResponseDTO generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);  // Ensure both token and username are passed here
    }
}