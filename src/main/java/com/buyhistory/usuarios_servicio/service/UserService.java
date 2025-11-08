package com.buyhistory.usuarios_servicio.service;

import com.buyhistory.usuarios_servicio.dto.RegisterRequest;
import com.buyhistory.usuarios_servicio.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto login(String email, String rawPassword);

    UserDto register(RegisterRequest request);

    List<UserDto> findAll();

    UserDto updateRole(String userId, String newRole);
}
