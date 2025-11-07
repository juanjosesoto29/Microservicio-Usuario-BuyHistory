package com.buyhistory.usuarios_servicio.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    // Para la tarea lo dejamos sin encriptar (NO para producción)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // "admin", "cliente", etc.
}
