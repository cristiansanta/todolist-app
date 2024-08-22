package com.mindhub.todolist.repositories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.util.Optional;

import com.mindhub.todolist.entities.UserEntity;
import com.mindhub.todolist.entities.UserRole;
import org.hamcrest.Matcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


@DataJpaTest
class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail_shouldReturnUser_whenUserExists() {
        // Arrange
        UserEntity newUser = new UserEntity(null, "testUser", "test@example.com", "password", UserRole.ROLE_USER);
        entityManager.persist(newUser);
        entityManager.flush();

        // Act
        Optional<UserEntity> foundOptional = userRepository.findByEmail("test@example.com");

        // Assert
        assertTrue(foundOptional.isPresent());
        UserEntity found = foundOptional.get();
        assertEquals("testUser", found.getUsername());
        assertEquals("test@example.com", found.getEmail());
    }

    @Test
    void findByEmail_shouldReturnEmpty_whenUserDoesNotExist() {
        // Act
        Optional<UserEntity> found = userRepository.findByEmail("nonexistent@example.com");

        // Assert
        assertTrue(found.isEmpty());
    }
}