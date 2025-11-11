package com.buyhistory.usuarios_servicio;

import com.buyhistory.usuarios_servicio.model.User;
import com.buyhistory.usuarios_servicio.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.List;

@SpringBootApplication
public class UsuariosServicioApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsuariosServicioApplication.class, args);
    }

    
    @Bean
    CommandLineRunner initUsers(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            repo.deleteAll();   // 🔁 sobrescribe siempre

            User admin = User.builder()
                    .name("Admin BuyHistory")
                    .email("juanjose@ofivirtual.cl")
                    .password(encoder.encode("admin12345"))
                    .role("admin")
                    .createdAt(Instant.now())
                    .build();

            User client = User.builder()
                    .name("Cliente Soto")
                    .email("sotojuaco@gmail.com")
                    .password(encoder.encode("asd123"))
                    .role("client")
                    .createdAt(Instant.now())
                    .build();

            repo.save(admin);
            repo.save(client);

            System.out.println("✅ Usuarios iniciales creados en MongoDB (admin y cliente).");
        };
    }


}
