package com.abdulkadir.auth.controller;

import com.abdulkadir.auth.dto.request.AuthRequest;
import com.abdulkadir.auth.dto.request.UserCreateDTO;
import com.abdulkadir.auth.service.AuthService;
import com.abdulkadir.auth.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private final AuthService authService;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtService jwtService;

    @PostMapping("/token")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        logger.info("Starting token generation for user: {}", authRequest.getUsername());
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.getUsername());
            logger.info("Token generated successfully for user: {}", authRequest.getUsername());
            return token;
        } else {
            logger.error("Invalid access attempt for user: {}", authRequest.getUsername());
            throw new RuntimeException("Invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam String token) {
        logger.info("Validating token");
        boolean isValid = jwtService.validateToken(token);
        return isValid ? "Token is valid." : "Token is invalid.";
    }

    @PostMapping("/register")
    public String createUser(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        logger.info("Registering new user: {}", userCreateDTO.getUsername());
        return authService.createUser(userCreateDTO);
    }
}