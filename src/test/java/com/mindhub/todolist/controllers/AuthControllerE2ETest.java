package com.mindhub.todolist.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.todolist.dtos.RegisterRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerUser_shouldReturnOk_whenValidInput() throws Exception {
        /**
         * Arrange-Act-Assert (AAA):
         * Este patrón divide el test en tres partes claras:
         * a) Arrange (Preparar): En esta fase se prepara todo lo necesario para el test. Esto incluye crear objetos, configurar mocks, y establecer el estado inicial necesario.
         * b) Act (Actuar): Aquí se realiza la acción que se está probando. Generalmente es una llamada al método que se está testeando.
         * c) Assert (Afirmar): En esta parte se verifica que el resultado de la acción es el esperado. Se utilizan aserciones para comparar el resultado actual con el esperado.
         * */
        // Arrange
        RegisterRequestDTO registerRequest = new RegisterRequestDTO("newuser@example.com", "StrongPassword1!");

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully!"));
    }

    @Test
    void registerUser_shouldReturnBadRequest_whenInvalidEmail() throws Exception {
        // Arrange
        RegisterRequestDTO registerRequest = new RegisterRequestDTO("invalidemail", "StrongPassword1!");

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid email format")));
    }
}