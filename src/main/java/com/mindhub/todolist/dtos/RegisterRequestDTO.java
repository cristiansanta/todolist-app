package com.mindhub.todolist.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
public class RegisterRequestDTO {
    @Schema(description = "New user's email address", example = "newuser@example.com")
    private String email;

    @Schema(description = "New user's password", example = "securepassword123")
    private String password;
}
