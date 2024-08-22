package com.mindhub.todolist.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@Tag(name = "Public", description = "Public endpoints")
public class PublicController {

    @GetMapping("/welcome")
    @Operation(
            summary = "Welcome message",
            description = "Returns a welcome message without requiring authentication",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response")
            }
    )
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome to the Todo List API!");
    }
}