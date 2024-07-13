package com.abdulkadir.advert.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String username;
    private String phoneNumber;
    private String address;
}
