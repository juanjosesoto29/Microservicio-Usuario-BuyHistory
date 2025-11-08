package com.buyhistory.usuarios_servicio.web;

import com.buyhistory.usuarios_servicio.dto.*;
import com.buyhistory.usuarios_servicio.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    // LOGIN
    @PostMapping("/auth/login")
    public UserDto login(@RequestBody LoginRequest request) {
        return userService.login(request.getEmail(), request.getPassword());
    }

    // REGISTRO
    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    // LISTAR USUARIOS (para admin / dashboard)
    @GetMapping("/users")
    public List<UserDto> getAll() {
        return userService.findAll();
    }

    // CAMBIAR ROL
    @PatchMapping("/users/{id}/role")
    public UserDto updateRole(@PathVariable String id, @RequestBody UpdateRoleRequest request) {
        return userService.updateRole(id, request.getRole());
    }
}
