package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.UserDTO;
import com.mindhub.todolist.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "Admin management APIs")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @PutMapping("/users/{id}/promote")
    @Operation(summary = "Promote user to admin", description = "Promotes a user to admin role")
    @ApiResponse(responseCode = "200", description = "User promoted successfully")
    public ResponseEntity<UserDTO> promoteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.promoteToAdmin(id));
    }

    @PutMapping("/users/{id}/demote")
    @Operation(summary = "Demote admin to user", description = "Demotes an admin to user role")
    @ApiResponse(responseCode = "200", description = "User demoted successfully")
    public ResponseEntity<UserDTO> demoteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.demoteToUser(id));
    }
}