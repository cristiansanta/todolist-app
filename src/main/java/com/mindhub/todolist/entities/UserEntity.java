package com.mindhub.todolist.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")  // Mantenemos el nombre de la tabla como "users"
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    private String role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Task> tasks;
}
