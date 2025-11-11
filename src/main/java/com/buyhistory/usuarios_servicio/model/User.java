package com.buyhistory.usuarios_servicio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String name;
    private String email;
    private String password;  // contraseña encriptada
    private String role;      // "admin" o "client"

    private Instant createdAt;  // 👈 ESTE ES EL CAMPO IMPORTANTE
}
