package com.buyhistory.usuarios_servicio.web;

import com.buyhistory.usuarios_servicio.dto.ApiResponse;
import com.buyhistory.usuarios_servicio.dto.LoginRequest;
import com.buyhistory.usuarios_servicio.dto.RegisterRequest;
import com.buyhistory.usuarios_servicio.dto.UpdateRoleRequest;
import com.buyhistory.usuarios_servicio.dto.UserDto;
import com.buyhistory.usuarios_servicio.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    private final UserService userService;

    // ============================
    // LOGIN
    // ============================
    @PostMapping("/auth/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequest request) {
        UserDto user = userService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(user);
    }

    // ============================
    // REGISTRO
    // ============================
    @PostMapping("/auth/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest request) {
        UserDto user = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // ============================
    // LISTAR USUARIOS
    // ============================
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    // ============================
    // CAMBIAR ROL
    // ============================
    @PatchMapping("/users/{id}/role")
    public ResponseEntity<ApiResponse> updateRole(
            @PathVariable Long id,
            @RequestBody UpdateRoleRequest request
    ) {
        UserDto updated = userService.updateRole(id, request.getRole());

        ApiResponse response = ApiResponse.builder()
                .message("Rol actualizado a " + updated.getRole() +
                         " para el usuario con id " + updated.getId())
                .status(HttpStatus.OK.value())
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    // ============================
    // ELIMINAR USUARIO
    // ============================
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        ApiResponse response = ApiResponse.builder()
                .message("Usuario " + id + " eliminado correctamente")
                .status(HttpStatus.OK.value())
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }
}
