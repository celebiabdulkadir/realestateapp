package com.abdulkadir.user.mapper;

import com.abdulkadir.user.dto.request.UserRequestDTO;
import com.abdulkadir.user.dto.response.UserResponseDTO;
import com.abdulkadir.user.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public  UserResponseDTO toUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public  User toUser(UserRequestDTO userRequestDTO) {
        return User.builder()
                .name(userRequestDTO.getName())
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .build();
    }

    public User updateUserFields(User existingUser, UserRequestDTO userRequestDTO) {
        existingUser.setName(userRequestDTO.getName());
        existingUser.setEmail(userRequestDTO.getEmail());
        existingUser.setPassword(userRequestDTO.getPassword());
        return existingUser;
    }
}
