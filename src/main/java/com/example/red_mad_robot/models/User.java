package com.example.red_mad_robot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "email не может быть пустым")
    private String email;
    @NotBlank(message = "пароль не может быть пустым")
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
