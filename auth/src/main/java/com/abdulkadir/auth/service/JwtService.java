package com.abdulkadir.auth.service;

import com.abdulkadir.auth.dto.response.AuthResponseDTO;
import com.abdulkadir.auth.exception.EntityNotFoundException;
import com.abdulkadir.auth.exception.TokenValidationException;
import com.abdulkadir.auth.model.Auth;
import com.abdulkadir.auth.repository.AuthRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Autowired
    private  AuthRepository repository;

    @Value("${jwt.secret}")
    private String SECRET;

    public AuthResponseDTO generateToken(String username) {

        Auth user = repository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Map<String, Object> claims = new HashMap<>();
        String token = createToken(claims, username);
        return new AuthResponseDTO(username, user.getEmail(), user.getName(), user.getSurname(), token, user.getId().toString());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 minutes
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) throws TokenValidationException {
        try {
            extractClaims(token); // If this does not throw an exception, the token is valid
            return true;
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            throw new TokenValidationException("Expired JWT token", HttpStatus.UNAUTHORIZED);
        } catch (io.jsonwebtoken.JwtException | IllegalArgumentException e) {
            throw new TokenValidationException("Invalid JWT token", HttpStatus.UNAUTHORIZED);
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}