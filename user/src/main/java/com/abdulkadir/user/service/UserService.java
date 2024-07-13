package com.abdulkadir.user.service;

import com.abdulkadir.user.dto.request.UserRequestDTO;
import com.abdulkadir.user.dto.response.UserResponseDTO;
import com.abdulkadir.user.exception.EntityAlreadyExistsException;
import com.abdulkadir.user.exception.EntityNotFoundException;
import com.abdulkadir.user.mapper.UserMapper;
import com.abdulkadir.user.model.User;
import com.abdulkadir.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public List<UserResponseDTO> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::toUserResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toUserResponseDTO).orElseThrow(
                () -> new EntityNotFoundException("User not found with id: " + id)
        );
    }
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }



    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        // Assuming email is the unique identifier. Adjust according to your user model.
        String email = userRequestDTO.getEmail();
        boolean userExists = userRepository.findByEmail(email).isPresent();

        if (userExists) {
            throw new EntityAlreadyExistsException("User already exists with email: " + email);
        }

        return userMapper.toUserResponseDTO(userRepository.save(userMapper.toUser(userRequestDTO)));
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        return userRepository.findById(id).map(user -> {
            User updatedUser = userMapper.updateUserFields(user, userRequestDTO);
            User savedUser = userRepository.save(updatedUser);
            return userMapper.toUserResponseDTO(savedUser);
        }).orElseThrow(
                () -> new EntityNotFoundException("User not found with id: " + id)
        );
    }
}
