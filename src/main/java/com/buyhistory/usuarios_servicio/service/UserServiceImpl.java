package com.buyhistory.usuarios_servicio.service;

import com.buyhistory.usuarios_servicio.dto.LoginRequest;
import com.buyhistory.usuarios_servicio.dto.RegisterRequest;
import com.buyhistory.usuarios_servicio.dto.UserDto;
import com.buyhistory.usuarios_servicio.model.User;
import com.buyhistory.usuarios_servicio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private UserDto toDto(User user) {
        if (user == null) return null;
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    @Override
public UserDto register(RegisterRequest request) {
    String email = request.getEmail().trim();

    // Verificar correo único
    userRepository.findByEmailIgnoreCase(email)
            .ifPresent(u -> {
                throw new RuntimeException("El correo ya está registrado");
            });

    User user = User.builder()
            .name(request.getName())
            .email(email)
            .password(request.getPassword()) // texto plano para demo
            .role("cliente")
            .build();

    User saved = userRepository.save(user);
    return toDto(saved);
}


    @Override
public UserDto login(LoginRequest request) {
    String email = request.getEmail().trim();

    return userRepository.findByEmail(email)
            .filter(u -> u.getPassword().equals(request.getPassword()))
            .map(this::toDto)
            .orElse(null);
}


    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
public UserDto updateRole(Long id, String role) {
    return userRepository.findById(id)
            .map(user -> {
                user.setRole(role);
                User saved = userRepository.save(user);
                return toDto(saved);
            })
            .orElse(null);
}

}
