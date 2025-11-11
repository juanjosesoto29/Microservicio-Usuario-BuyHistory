package com.buyhistory.usuarios_servicio.web;

import com.buyhistory.usuarios_servicio.dto.LoginRequest;
import com.buyhistory.usuarios_servicio.dto.RegisterRequest;
import com.buyhistory.usuarios_servicio.dto.UpdateRoleRequest;
import com.buyhistory.usuarios_servicio.model.User;
import com.buyhistory.usuarios_servicio.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request) {
        return service.login(request.getEmail(), request.getPassword());
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return service.register(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );
    }

    @GetMapping
    public List<User> findAll() {
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}/role")
    public User updateRole(@PathVariable String id,
                           @RequestBody UpdateRoleRequest request) {
        return service.updateRole(id, request.getRole());
    }
}
