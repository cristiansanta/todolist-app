package com.mindhub.todolist.controllers;

import com.mindhub.todolist.dtos.LoginRequestDTO;
import com.mindhub.todolist.dtos.RegisterRequestDTO;
import com.mindhub.todolist.entities.UserEntity;
import com.mindhub.todolist.entities.UserRole;
import com.mindhub.todolist.repositories.UserRepository;
import com.mindhub.todolist.security.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;


    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?=\\S+$).{8,}$");
    private static final int MAX_EMAIL_LENGTH = 320; // RFC 5321
    private static final int MAX_PASSWORD_LENGTH = 72; // Common bcrypt limit

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Register a new user with username, email and password",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDTO registerRequest) {

        String username = registerRequest.getUsername();
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Username cannot be empty!");
        }
        if (username.length() > 50) {
            return ResponseEntity.badRequest().body("Error: Username is too long!");
        }

        // Validate email
        String email = sanitizeInput(registerRequest.getEmail());
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Email cannot be empty!");
        }
        if (email.length() > MAX_EMAIL_LENGTH) {
            return ResponseEntity.badRequest().body("Error: Email is too long!");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return ResponseEntity.badRequest().body("Error: Invalid email format!");
        }
        email = email.toLowerCase(); // Normalize email

        // Validate password
        String password = registerRequest.getPassword();
        if (password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Password cannot be empty!");
        }
        if (password.length() > MAX_PASSWORD_LENGTH) {
            return ResponseEntity.badRequest().body("Error: Password is too long!");
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            return ResponseEntity.badRequest().body("Weak password: Password must be at least 8 characters long, include one " +
                    "digit, one lowercase letter, one uppercase letter, one special character, and no whitespace!");
        }

        // Check if email is already in use
        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // Create new user
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(UserRole.ROLE_USER);

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    private String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        // Remove any character that isn't a letter, number, or common email symbols
        return input.replaceAll("[^a-zA-Z0-9@.+\\-_]", "").trim();
    }

    @PostMapping("/login")
    @Operation(
            summary = "Authenticate user",
            description = "Authenticate a user and return a JWT token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Authentication successful"),
                    @ApiResponse(responseCode = "401", description = "Authentication failed")
            }
    )
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(jwt);
    }
}