package com.mindhub.todolist.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class RegisterRequestDTO {

    @Schema(description = "User's username", example = "cristiano")
    private String username;

    @Schema(description = "User's email address", example = "user@example.com")
    private String email;

    @Schema(description = "User's password", example = "password123")
    private String password;
}