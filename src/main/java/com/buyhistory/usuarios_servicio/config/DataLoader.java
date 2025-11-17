package com.buyhistory.usuarios_servicio.config;

import com.buyhistory.usuarios_servicio.entity.Usuario;
import com.buyhistory.usuarios_servicio.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initUsuarios(UsuarioRepository repo,
                                   PasswordEncoder encoder) {
        return args -> {
            if (repo.count() == 0) {
                Usuario admin = Usuario.builder()
                        .name("Admin BuyHistory")
                        .email("admin@buyhistory.cl")
                        .password(encoder.encode("admin123"))
                        .role("ADMIN")
                        .enabled(true)
                        .build();

                Usuario cliente = Usuario.builder()
                        .name("Cliente Demo")
                        .email("cliente@buyhistory.cl")
                        .password(encoder.encode("cliente123"))
                        .role("CLIENTE")
                        .enabled(true)
                        .build();

                repo.save(admin);
                repo.save(cliente);

                System.out.println("✅ Usuarios iniciales creados en MySQL (admin y cliente).");
            } else {
                System.out.println("ℹ️ Tabla 'usuarios' ya tiene datos, no se hace seed.");
            }
        };
    }
}
