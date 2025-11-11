package com.buyhistory.usuarios_servicio.web;

import com.buyhistory.usuarios_servicio.dto.*;
import com.buyhistory.usuarios_servicio.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/ping")
    public String ping() {
        return "OK usuarios-servicio";
    }

    @PostMapping("/auth/login")
    public UserDto login(@RequestBody LoginRequest req) {
        return service.login(req);
    }

    @PostMapping("/auth/register")
    public ApiResponse register(@RequestBody RegisterRequest req) {
        return service.register(req);
    }

    @GetMapping("/users")
    public List<UserDto> findAll() {
        return service.findAll();
    }

    @PatchMapping("/users/{id}/role")
    public UserDto updateRole(@PathVariable String id,
                              @RequestBody UpdateRoleRequest req) {
        return service.updateRole(id, req.getRole());
    }
}
