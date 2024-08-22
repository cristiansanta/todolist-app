package com.mindhub.todolist.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.mindhub.todolist.dtos.UserDTO;
import com.mindhub.todolist.entities.UserEntity;
import com.mindhub.todolist.entities.UserRole;
import com.mindhub.todolist.exceptions.ResourceNotFoundException;
import com.mindhub.todolist.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserById_shouldReturnUserDTO_whenUserExists() {
        // Arrange
        Long userId = 1L;
        UserEntity mockUser = new UserEntity(userId, "testUser", "test@example.com", "password", UserRole.ROLE_USER);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        UserDTO result = userService.getUserById(userId);

        // Assert
        assertThat(result, notNullValue());
        assertThat(result.getId(), is(userId));
        assertThat(result.getUsername(), equalTo("testUser"));
        assertThat(result.getEmail(), equalTo("test@example.com"));
        assertThat(result.getRole(), equalTo("ROLE_USER"));

        verify(userRepository).findById(userId);
    }

    @Test
    void getUserById_shouldThrowException_whenUserNotFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(userId));
        verify(userRepository).findById(userId);
    }
}