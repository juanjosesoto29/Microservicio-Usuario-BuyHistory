package com.buyhistory.usuarios_servicio.service;

import com.buyhistory.usuarios_servicio.dto.RegisterRequest;
import com.buyhistory.usuarios_servicio.dto.UserDto;
import com.buyhistory.usuarios_servicio.entity.Usuario;
import com.buyhistory.usuarios_servicio.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    private UserDto map(Usuario u) {
        return UserDto.builder()
                .id(u.getId())
                .name(u.getName())
                .email(u.getEmail())
                .role(u.getRole())
                .enabled(u.isEnabled())
                .isAdmin("ADMIN".equalsIgnoreCase(u.getRole()))
                .build();
    }

    @Override
    public UserDto login(String email, String rawPassword) {
        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(rawPassword, usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return map(usuario);
    }

    @Override
    public UserDto register(RegisterRequest request) {
        // Validar correo repetido
        repository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("El correo ya está registrado");
                });

        Usuario usuario = Usuario.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("CLIENTE")  // por defecto
                .enabled(true)
                .build();

        return map(repository.save(usuario));
    }

    @Override
    public List<UserDto> findAll() {
        return repository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public UserDto updateRole(Long userId, String newRole) {
        Usuario usuario = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setRole(newRole);
        return map(repository.save(usuario));
    }

    @Override
    public void deleteUser(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        repository.delete(usuario);
    }
}
