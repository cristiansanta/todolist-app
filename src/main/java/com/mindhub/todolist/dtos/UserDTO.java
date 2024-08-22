package com.mindhub.todolist.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;

    @Schema(description = "Username of the user", example = "john_doe")
    private String username;

    @Schema(description = "Email address of the user", example = "john@example.com")
    private String email;

    @Schema(description = "Role of the user", example = "ROLE_USER")
    private String role;
}