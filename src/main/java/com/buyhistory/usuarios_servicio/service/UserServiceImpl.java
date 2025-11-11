package com.buyhistory.usuarios_servicio.service;

import com.buyhistory.usuarios_servicio.model.User;
import com.buyhistory.usuarios_servicio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User login(String email, String rawPassword) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Comparamos la contraseña en texto plano con la encriptada
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return user;
    }

    @Override
    public User register(String name, String email, String rawPassword) {
        if (repository.findByEmail(email).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        User user = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(rawPassword))
                .role("client")           // por defecto cliente
                .createdAt(Instant.now())
                .build();

        return repository.save(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public User updateRole(String id, String role) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setRole(role);
        return repository.save(user);
    }
}
