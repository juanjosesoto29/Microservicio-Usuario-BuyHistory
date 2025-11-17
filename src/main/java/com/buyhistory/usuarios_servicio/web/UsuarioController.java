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
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // para desarrollo, luego puedes restringir
public class UsuarioController {

    private final UserService userService;

    // POST /api/v1/usuarios/login
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequest request) {
        UserDto user = userService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(user);
    }

    // POST /api/v1/usuarios/register
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest request) {
        UserDto user = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // GET /api/v1/usuarios
    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    // PATCH /api/v1/usuarios/{id}/rol
    // Body: { "role": "ADMIN" }
    @PatchMapping("/{id}/rol")
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
}
