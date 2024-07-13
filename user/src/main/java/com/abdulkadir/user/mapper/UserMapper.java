package com.abdulkadir.user.mapper;

import com.abdulkadir.user.dto.request.UserRequestDTO;
import com.abdulkadir.user.dto.response.UserResponseDTO;
import com.abdulkadir.user.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserResponseDTO toUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .build();
    }

    public User toUser(UserRequestDTO userRequestDTO) {
        return User.builder()
                .name(userRequestDTO.getName())
                .surname(userRequestDTO.getSurname())
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .username(userRequestDTO.getUsername())
                .phoneNumber(userRequestDTO.getPhoneNumber())
                .address(userRequestDTO.getAddress())
                .build();
    }

    public User updateUserFields(User existingUser, UserRequestDTO userRequestDTO) {
        existingUser.setName(userRequestDTO.getName());
        existingUser.setSurname(userRequestDTO.getSurname());
        existingUser.setEmail(userRequestDTO.getEmail());
        existingUser.setPassword(userRequestDTO.getPassword());
        existingUser.setUsername(userRequestDTO.getUsername());
        existingUser.setPhoneNumber(userRequestDTO.getPhoneNumber());
        existingUser.setAddress(userRequestDTO.getAddress());
        return existingUser;
    }
}
