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

// 🔽 IMPORTS SWAGGER (solo estos para evitar conflicto con tu ApiResponse)
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@Tag(
        name = "Usuarios y autenticación",
        description = "Endpoints para login, registro, gestión de usuarios y roles en BuyHistory"
)
public class UsuarioController {

    private final UserService userService;

    // ============================
    // LOGIN
    // ============================

    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica al usuario por email y contraseña y retorna los datos básicos del usuario."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Login exitoso"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "401",
            description = "Credenciales inválidas"
    )
    @PostMapping("/auth/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequest request) {
        UserDto user = userService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(user);
    }

    // ============================
    // REGISTRO
    // ============================

    @Operation(
            summary = "Registrar nuevo usuario",
            description = "Crea un nuevo usuario en el sistema con los datos entregados en el cuerpo de la petición."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Usuario registrado correctamente"
    )
    @PostMapping("/auth/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest request) {
        UserDto user = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // ============================
    // LISTAR USUARIOS
    // ============================

    @Operation(
            summary = "Listar usuarios",
            description = "Obtiene el listado completo de usuarios registrados en el sistema."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Listado obtenido correctamente"
    )
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    // ============================
    // CAMBIAR ROL
    // ============================

    @Operation(
            summary = "Actualizar rol de usuario",
            description = "Actualiza el rol de un usuario (por ejemplo, de USER a ADMIN) usando su ID."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Rol actualizado correctamente"
    )
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

    @Operation(
            summary = "Eliminar usuario",
            description = "Elimina un usuario del sistema utilizando su ID."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Usuario eliminado correctamente"
    )
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
