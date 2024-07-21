package com.abdulkadir.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    private String username;
    private String email;
    private String name;
    private String surname;
    private String token;
    private String userId;
}
