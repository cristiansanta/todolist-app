package com.mindhub.todolist.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
public class LoginRequestDTO {
    @Schema(description = "User's email address", example = "user@example.com")
    private String email;

    @Schema(description = "User's password", example = "password123")
    private String password;
}
