package com.buyhistory.usuarios_servicio.service;

import com.buyhistory.usuarios_servicio.dto.*;
import com.buyhistory.usuarios_servicio.model.User;
import com.buyhistory.usuarios_servicio.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    // ====== helpers de mapeo ======

    private UserDto mapToDto(User u) {
        return new UserDto(
                u.getId(),
                u.getName(),
                u.getEmail(),
                u.getRole()
        );
    }

    // ====== implementación de métodos ======

    @Override
    public UserDto login(LoginRequest request) {
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "Correo o contraseña incorrectos")
                );

        // Por simplicidad usamos contraseña en texto plano
        if (!user.getPassword().equals(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Correo o contraseña incorrectos");
        }

        return mapToDto(user);
    }

    @Override
    public ApiResponse register(RegisterRequest request) {
        boolean exists = repository.findByEmail(request.getEmail()).isPresent();
        if (exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo ya está registrado");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // texto plano para mantenerlo simple
        user.setRole("CLIENTE");

        repository.save(user);

        return new ApiResponse("Usuario registrado exitosamente");
    }

    @Override
    public List<UserDto> findAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public UserDto updateRole(String id, String role) {
        User user = repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado")
                );

        user.setRole(role);
        repository.save(user);

        return mapToDto(user);
    }
}
