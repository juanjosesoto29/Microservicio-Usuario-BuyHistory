package com.buyhistory.usuarios_servicio.web;

import com.buyhistory.usuarios_servicio.dto.*;
import com.buyhistory.usuarios_servicio.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.buyhistory.usuarios_servicio.dto.UpdateRoleRequest;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        UserDto user = userService.findById(id);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    try {
        UserDto created = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    } catch (RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(ex.getMessage()));
    }
}


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        UserDto user = userService.login(request);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("Credenciales inválidas"));
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<?> updateRole(@PathVariable Long id,
                                    @RequestBody UpdateRoleRequest request) {
    UserDto updated = userService.updateRole(id, request.getRole());
    if (updated == null) {
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(updated);
}
}
