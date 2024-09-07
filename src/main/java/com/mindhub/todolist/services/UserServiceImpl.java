package com.mindhub.todolist.services;

import com.mindhub.todolist.dtos.UserDTO;
import com.mindhub.todolist.entities.UserEntity;
import com.mindhub.todolist.entities.UserRole;
import com.mindhub.todolist.exceptions.ResourceNotFoundException;
import com.mindhub.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setRole(UserRole.ROLE_USER);
        user = userRepository.save(user);
        return convertToDTO(user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return convertToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user = userRepository.save(user);
        return convertToDTO(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO convertToDTO(UserEntity user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole().name());
    }
}