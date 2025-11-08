package com.buyhistory.usuarios_servicio;

import com.buyhistory.usuarios_servicio.model.User;
import com.buyhistory.usuarios_servicio.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class UsuariosServicioApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsuariosServicioApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner initUsers(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.count() == 0) {
                User admin = User.builder()
                        .name("Admin BuyHistory")
                        .email("juanjose@ofivirtual.cl")
                        .password(encoder.encode("admin123"))
                        .role("ADMIN")
                        .enabled(true)
                        .build();

                User cliente = User.builder()
                        .name("Cliente Demo")
                        .email("sotojuaco@gmail.com")
                        .password(encoder.encode("cliente123"))
                        .role("CLIENTE")
                        .enabled(true)
                        .build();

                repo.saveAll(List.of(admin, cliente));
                System.out.println("✅ Usuarios iniciales creados en MongoDB (admin y cliente).");
            } else {
                System.out.println("ℹ️ Colección 'users' ya tiene datos, no se hace seed.");
            }
        };
    }
}
